package com.alex.notesbackend.repository.note

import com.alex.notesbackend.repository.note.DbModelNote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NoteDao : JpaRepository<DbModelNote, Long> {
    fun findAllByUserId(userId: Long): List<DbModelNote>

    fun findByIdAndUserId(id: Long, userId: Long): DbModelNote?

    fun deleteByUserId(userId: Long): Boolean
    fun deleteByIdAndUserId(id: Long, userId: Long): Boolean
}