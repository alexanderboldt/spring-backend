package com.alex.moviebackend.repository.database.session

import org.springframework.data.repository.CrudRepository

interface SessionRepository : CrudRepository<DbModelSession, Long>