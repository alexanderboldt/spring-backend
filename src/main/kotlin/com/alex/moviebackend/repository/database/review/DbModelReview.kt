package com.alex.moviebackend.repository.database.review

import com.alex.moviebackend.repository.database.movie.DbModelMovie
import javax.persistence.*

@Entity
data class DbModelReview(
    @Id
    @GeneratedValue
    val id: Long,

    val userId: Long,

    @ManyToOne
    val movie: DbModelMovie,

    val description: String,

    val createdAt: Long
)