package com.alex.notesbackend.repository.note

import javax.persistence.*

@Entity
data class DbModelNote(
    @Id
    @GeneratedValue
    var id: Long,

    @Column(nullable = false)
    var userId: Long,

    @Column(nullable = false)
    var title: String,

    var description: String?,

    @Column(nullable = false)
    var createdAt: Long,

    @Column(nullable = false)
    var updatedAt: Long
)