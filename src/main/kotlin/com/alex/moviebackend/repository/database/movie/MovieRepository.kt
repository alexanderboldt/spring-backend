package com.alex.moviebackend.repository.database.movie

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<DbModelMovie, Long> {

    @Query("from DbModelMovie")
    fun findAllSorted(sort: Sort): List<DbModelMovie>
}