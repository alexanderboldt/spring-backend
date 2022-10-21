package com.alex.moviebackend.controller

import com.alex.moviebackend.exception.ResourceNotFoundException
import com.alex.moviebackend.repository.mapping.toApiModelGet
import com.alex.moviebackend.repository.api.movie.ApiModelMovieGet
import com.alex.moviebackend.repository.api.movie.ApiModelMoviePost
import com.alex.moviebackend.repository.api.movie.ApiModelMoviePut
import com.alex.moviebackend.repository.database.movie.DbModelMovie
import com.alex.moviebackend.repository.database.movie.MovieRepository
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.Date

@RestController
@RequestMapping("/api/v1/movies")
class MovieController(private val movieRepository: MovieRepository) {

    private val textMovieNotFoundWithGivenId = "Movie with the given id not found!"

    // create

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postMovie(@RequestBody request: ApiModelMoviePost): ApiModelMovieGet {
        return movieRepository
            .save(DbModelMovie(0, request.title, request.description, Date().time))
            .toApiModelGet()
    }

    // read

    @GetMapping
    fun getAllMovies(
        @RequestParam sort: Sort?,
        @RequestParam offset: Int?,
        @RequestParam limit: Int?,
    ): List<ApiModelMovieGet> {
        return movieRepository
            .findAllSorted(sort ?: Sort.by("id"))
            .let { movies -> if (offset != null && offset >= 1) movies.drop(offset) else movies }
            .let { movies -> if (limit != null && limit >= 1) movies.take(limit) else movies }
            .toApiModelGet()
    }

    @GetMapping("{id}")
    fun getMovie(@PathVariable("id") id: Long): ApiModelMovieGet {
        return movieRepository
            .findByIdOrNull(id)
            ?.toApiModelGet()
            ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)
    }

    // update

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody request: ApiModelMoviePut
    ): ApiModelMovieGet {
        val movie = movieRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)

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
    fun deleteMovie(@PathVariable("id") id: Long) {
        movieRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(textMovieNotFoundWithGivenId)
        movieRepository.deleteById(id)
    }
}