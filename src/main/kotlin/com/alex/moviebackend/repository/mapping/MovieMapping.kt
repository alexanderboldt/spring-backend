package com.alex.moviebackend.repository.mapping

import com.alex.moviebackend.repository.api.movie.ApiModelMovieGet
import com.alex.moviebackend.repository.database.movie.DbModelMovie

// from database to api

fun Iterable<DbModelMovie>.toApiModelGet() = map { it.toApiModelGet() }

fun DbModelMovie.toApiModelGet() = ApiModelMovieGet(id, title, description, createdAt)