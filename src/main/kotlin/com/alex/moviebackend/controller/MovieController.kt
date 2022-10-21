package com.alex.moviebackend.controller

import com.alex.moviebackend.exception.ResourceNotFoundException
import com.alex.moviebackend.mapping.toRestModelGet
import com.alex.moviebackend.repository.DbModelMovie
import com.alex.moviebackend.repository.MovieRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Date

@RestController
@RequestMapping("/movies")
class MovieController(private val movieRepository: MovieRepository) {

    // create

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postMovie(@RequestBody request: RestModelMoviePost): RestModelMovieGet {
        return movieRepository
            .save(DbModelMovie(0, request.title, request.description, Date().time))
            .toRestModelGet()
    }

    // read

    @GetMapping
    fun getAllMovies() = movieRepository.findAll().toRestModelGet()

    // update

    @PutMapping("{id}")
    fun update(
        @PathVariable("id") id: Long,
        @RequestBody request: RestModelMoviePut
    ): RestModelMovieGet {
        val movie = movieRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Movie with the given id not found!")

        return movieRepository.save(
            movie.copy(
                title = request.title ?: movie.title,
                description = request.description ?: movie.description
            )
        ).toRestModelGet()
    }

    // delete

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMovie(@PathVariable("id") id: Long) {
        movieRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException("Movie with the given id not found!")
        movieRepository.deleteById(id)
    }
}