package com.alex.notesbackend.repository.session

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface SessionDao : JpaRepository<DbModelSession, Long> {
    fun findByToken(token: String): DbModelSession?
}