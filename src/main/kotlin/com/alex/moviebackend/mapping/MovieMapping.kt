package com.alex.moviebackend.mapping

import com.alex.moviebackend.controller.RestModelMovieGet
import com.alex.moviebackend.controller.RestModelMoviePost
import com.alex.moviebackend.repository.DbModelMovie

// from database to rest

fun Iterable<DbModelMovie>.toRestModelGet() = map { it.toRestModelGet() }

fun DbModelMovie.toRestModelGet() = RestModelMovieGet(id, title, description, createdAt)