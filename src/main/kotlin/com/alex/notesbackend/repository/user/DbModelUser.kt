package com.alex.notesbackend.repository.user

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DbModelUser(
    @Id
    @GeneratedValue
    val id: Long,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val password: String
)