package com.alex.notesbackend.features.user

import com.alex.notesbackend.features.user.model.LoginPostRequest
import com.alex.notesbackend.features.user.model.LoginPostResponse
import com.alex.notesbackend.repository.session.DbModelSession
import com.alex.notesbackend.repository.session.SessionDao
import com.alex.notesbackend.repository.user.UserDao
import com.alex.notesbackend.utils.AUTHORIZATION_TYPE_BEARER
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.random.Random

@RestController
class UserController(
    private val userDao: UserDao,
    private val sessionDao: SessionDao
) {

    companion object {
        const val loginPath = "/v1/login"
        const val logoutPath = "/v1/logout"
    }

    // ----------------------------------------------------------------------------

    @PostMapping(loginPath)
    fun login(@RequestBody loginRequest: LoginPostRequest): ResponseEntity<LoginPostResponse> {
        return userDao
            .findByUsernameAndPassword(loginRequest.username, loginRequest.password)
            ?.let { user ->
                sessionDao.save(
                    DbModelSession(
                        0,
                        user.id,
                        AUTHORIZATION_TYPE_BEARER,
                        Random.nextInt(100000, 999999).toString()
                    )
                )
            }?.let { session -> ResponseEntity(LoginPostResponse(session.type, session.token), HttpStatus.CREATED) }
            ?: ResponseEntity(null, HttpStatus.NOT_FOUND)
    }

    @PostMapping(logoutPath)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun logout(@RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String) {
        authorization
            .split(" ")
            .let { it[0] to it[1] }
            .let { (type, token) -> sessionDao.findByTypeAndToken(type, token) }
            ?.let { session -> sessionDao.deleteById(session.id) }
    }
}