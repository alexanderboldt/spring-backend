package com.alex.notesbackend.repository.user

import com.alex.notesbackend.repository.user.DbModelUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserDao : JpaRepository<DbModelUser, Long> {

    @Query("select * from DbModelUser where username = ?1 and password = ?2")
    fun findByUsernameAndPassword(username: String, password: String): DbModelUser?
}