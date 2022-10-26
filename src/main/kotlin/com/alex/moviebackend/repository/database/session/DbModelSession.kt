package com.alex.moviebackend.repository.database.session

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DbModelSession(
    @Id
    @GeneratedValue
    val id: Long,

    val userId: Long,

    val sessionToken: String,

    val refreshToken: String
)
