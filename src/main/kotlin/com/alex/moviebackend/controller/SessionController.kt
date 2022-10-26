package com.alex.moviebackend.controller

import com.alex.moviebackend.exception.ResourceNotFoundException
import com.alex.moviebackend.repository.api.session.ApiModelLoginGet
import com.alex.moviebackend.repository.api.session.ApiModelLoginPost
import com.alex.moviebackend.repository.database.session.DbModelSession
import com.alex.moviebackend.repository.database.session.SessionRepository
import com.alex.moviebackend.repository.database.user.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("api/v1")
class SessionController(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) {

    @PostMapping("login")
    fun login(@RequestBody request: ApiModelLoginPost): ApiModelLoginGet  {
        val user = userRepository
            .findByUsernameAndPassword(request.username, request.password)
            ?: throw ResourceNotFoundException("User not found!")

        return sessionRepository
            .save(DbModelSession(0, user.id, Random.nextInt(100000, 999999).toString(), Random.nextInt(100000, 999999).toString()))
            .let { ApiModelLoginGet(it.sessionToken, it.refreshToken) }
    }
}