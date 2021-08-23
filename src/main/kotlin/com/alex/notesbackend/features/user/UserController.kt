package com.alex.notesbackend.features.user

import com.alex.notesbackend.features.user.model.LoginPostRequest
import com.alex.notesbackend.features.user.model.LoginPostResponse
import com.alex.notesbackend.repository.session.DbModelSession
import com.alex.notesbackend.repository.session.SessionDao
import com.alex.notesbackend.repository.user.UserDao
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class UserController(
    private val userDao: UserDao,
    private val sessionDao: SessionDao) {

    @PostMapping("v1/login")
    fun login(@RequestBody loginRequest: LoginPostRequest): ResponseEntity<LoginPostResponse> {
        return userDao
            .findByUsernameAndPassword(loginRequest.username, loginRequest.password)
            ?.let { user -> sessionDao.save(DbModelSession(0, user.id, Random.nextInt(100000, 999999).toString())) }
            ?.let { session -> ResponseEntity(LoginPostResponse(session.token), HttpStatus.OK) }
            ?: ResponseEntity(null, HttpStatus.NOT_FOUND)
    }
}