package com.alex.moviebackend.repository.api.session

data class ApiModelLoginGet(
    val sessionToken: String,
    val refreshToken: String
)
