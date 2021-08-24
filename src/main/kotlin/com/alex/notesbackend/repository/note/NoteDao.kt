package com.alex.notesbackend.repository.note

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository

interface NoteDao : JpaRepository<DbModelNote, Long> {
    fun findAllByUserId(userId: Long, sort: Sort): List<DbModelNote>

    fun findByIdAndUserId(id: Long, userId: Long): DbModelNote?

    fun deleteByUserId(userId: Long): Boolean
    fun deleteByIdAndUserId(id: Long, userId: Long): Boolean
}