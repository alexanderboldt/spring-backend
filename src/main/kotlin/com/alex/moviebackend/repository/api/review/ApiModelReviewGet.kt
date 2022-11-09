package com.alex.moviebackend.repository.api.review

data class ApiModelReviewGet(
    val id: Long,
    val text: String,
    val createdAt: Long
)
