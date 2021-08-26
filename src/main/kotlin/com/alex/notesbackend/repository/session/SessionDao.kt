package com.alex.notesbackend.repository.session

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

interface SessionDao : JpaRepository<DbModelSession, Long> {
    fun findByTypeAndToken(type: String, token: String): DbModelSession?
}