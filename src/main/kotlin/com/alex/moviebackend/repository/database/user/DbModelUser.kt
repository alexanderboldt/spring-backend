package com.alex.moviebackend.repository.database.user

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DbModelUser(
    @Id
    @GeneratedValue
    val id: Long,

    @Column(unique = true)
    val username: String,

    val password: String,

    val createdAt: Long
)