package com.alex.notesbackend.repository.note

import com.alex.notesbackend.repository.note.DbModelNote
import org.springframework.data.jpa.repository.JpaRepository

interface NoteDao : JpaRepository<DbModelNote, Long>