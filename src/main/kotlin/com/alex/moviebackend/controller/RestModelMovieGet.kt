package com.alex.moviebackend.controller

data class RestModelMovieGet(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: Long
)
