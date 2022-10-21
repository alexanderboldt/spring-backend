package com.alex.moviebackend.repository.api.movie

data class ApiModelMovieGet(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: Long
)
