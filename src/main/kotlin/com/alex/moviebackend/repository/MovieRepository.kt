package com.alex.moviebackend.repository

import org.springframework.data.repository.CrudRepository

interface MovieRepository : CrudRepository<DbModelMovie, Long>