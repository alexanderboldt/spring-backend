package com.alex.moviebackend.repository.database.movie

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DbModelMovie(
    @Id
    @GeneratedValue
    val id: Long,

    val userId: Long,

    val title: String,

    val description: String?,

    val createdAt: Long
)
