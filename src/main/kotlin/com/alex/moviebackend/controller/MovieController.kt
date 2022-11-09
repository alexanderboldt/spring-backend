package com.alex.moviebackend.controller

import com.alex.moviebackend.exception.ResourceNotFoundException
import com.alex.moviebackend.repository.mapping.toApiModelGet
import com.alex.moviebackend.repository.api.movie.ApiModelMovieGet
import com.alex.moviebackend.repository.api.movie.ApiModelMoviePost
import com.alex.moviebackend.repository.api.movie.ApiModelMoviePut
import com.alex.moviebackend.repository.api.review.ApiModelReviewGet
import com.alex.moviebackend.repository.api.review.ApiModelReviewPost
import com.alex.moviebackend.repository.database.movie.DbModelMovie
import com.alex.moviebackend.repository.database.movie.MovieRepository
import com.alex.moviebackend.repository.database.review.DbModelReview
import com.alex.moviebackend.repository.database.review.ReviewRepository
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.Date

@RestController
@RequestMapping("/api/v1/movies")
class MovieController(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository
) {

    private val textMovieNotFoundWithGivenId = "Movie with the given id not found!"

    // create

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postMovie(@RequestBody request: ApiModelMoviePost, @RequestAttribute userId: Long): ApiModelMovieGet {
        return movieRepository
            .save(DbModelMovie(0, userId, request.title, request.description, Date().time))
            .toApiModelGet()
    }

    @PostMapping("{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    fun postReview(
        @PathVariable("id") id: Long,
        @RequestBody request: ApiModelReviewPost,
        @RequestAttribute userId: Long
    ): ApiModelReviewGet {
        val movie = movieRepository.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)

        return reviewRepository
            .save(DbModelReview(0, userId, movie.id, request.description, Date().time))
            .toApiModelGet()
    }

    // read

    @GetMapping
    fun getAllMovies(
        @RequestParam sort: Sort?,
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
        @RequestAttribute userId: Long
    ): List<ApiModelMovieGet> {
        return movieRepository
            .findAllByUserId(userId, sort ?: Sort.by("id"))
            .let { movies -> if (offset != null && offset >= 1) movies.drop(offset) else movies }
            .let { movies -> if (limit != null && limit >= 1) movies.take(limit) else movies }
            .toApiModelGet()
    }

    @GetMapping("{id}/reviews")
    fun getReviews(@PathVariable("id") id: Long, @RequestAttribute userId: Long): List<ApiModelReviewGet> {
        movieRepository.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)
        return reviewRepository.findAllByMovieId(id).toApiModelGet()
    }

    @GetMapping("{id}")
    fun getMovie(@PathVariable("id") id: Long, @RequestAttribute userId: Long): ApiModelMovieGet {
        return movieRepository
            .findByIdAndUserId(id, userId)
            ?.toApiModelGet()
            ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)
    }

    // update

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody request: ApiModelMoviePut,
        @RequestAttribute userId: Long
    ): ApiModelMovieGet {
        val movie = movieRepository.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)

        return movieRepository.save(
            movie.copy(
                title = request.title ?: movie.title,
                description = request.description ?: movie.description
            )
        ).toApiModelGet()
    }

    // delete

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovie(@PathVariable("id") id: Long, @RequestAttribute userId: Long) {
        movieRepository.findByIdAndUserId(id, userId) ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)

        // delete the connected reviews and the movie itself
        reviewRepository.deleteByMovieId(id)
        movieRepository.deleteById(id)
    }
}