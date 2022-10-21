package com.alex.moviebackend.repository.database.movie

import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<DbModelMovie, Long>