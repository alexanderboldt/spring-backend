package com.alex.moviebackend.repository.database.user

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<DbModelUser, Long> {

    @Query("select user from DbModelUser user where user.username = ?1 and user.password = ?2")
    fun findByUsernameAndPassword(username: String, password: String): DbModelUser?
}