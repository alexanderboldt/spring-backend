package com.alex.moviebackend.repository

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DbModelMovie(
    @Id
    @GeneratedValue
    val id: Long,

    val title: String,

    val description: String?,

    val createdAt: Long
)
