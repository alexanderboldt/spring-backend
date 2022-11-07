package com.alex.moviebackend.repository.database.movie

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<DbModelMovie, Long> {

    fun findAllByUserId(userId: Long, sort: Sort): List<DbModelMovie>

    fun findByIdAndUserId(id: Long, userId: Long): DbModelMovie?
}