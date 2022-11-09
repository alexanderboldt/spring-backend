package com.alex.moviebackend.repository.database.review

import javax.persistence.*

@Entity
data class DbModelReview(
    @Id
    @GeneratedValue
    val id: Long,

    val userId: Long,

    val movieId: Long,

    val description: String,

    val createdAt: Long
)