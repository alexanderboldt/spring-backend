package com.alex.moviebackend.repository.database.user

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<DbModelUser, Long> {

    fun findByUsername(username: String): DbModelUser?
}