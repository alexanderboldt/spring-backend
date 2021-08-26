package com.alex.notesbackend.repository.session

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DbModelSession(
    @Id
    @GeneratedValue
    val id: Long,

    @Column(nullable = false)
    val userId: Long,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val token: String
)