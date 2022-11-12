package com.alex.moviebackend.controller

import com.alex.moviebackend.util.JwtUtils
import com.alex.moviebackend.exception.BadRequestException
import com.alex.moviebackend.repository.api.user.ApiModelLoginGet
import com.alex.moviebackend.repository.api.user.ApiModelLoginPost
import com.alex.moviebackend.repository.api.user.ApiModelUserGet
import com.alex.moviebackend.repository.api.user.ApiModelRegisterPost
import com.alex.moviebackend.repository.database.user.DbModelUser
import com.alex.moviebackend.repository.database.user.UserRepository
import com.alex.moviebackend.repository.mapping.toApiModelGet
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/v1")
class UserController(
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {

    // minimum eight characters, at least one uppercase letter, one lowercase letter and one number
    private val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$".toRegex()

    // ----------------------------------------------------------------------------

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun postUser(@RequestBody request: ApiModelRegisterPost): ApiModelUserGet {
        if (userRepository.findByUsername(request.username) != null) {
            throw BadRequestException("User already exist with the given username!")
        }

        if (!regex.containsMatchIn(request.password)) {
            throw BadRequestException("Password didn't match the requirements!")
        }

        return userRepository
            .save(DbModelUser(0, request.username, passwordEncoder.encode(request.password), Date().time))
            .toApiModelGet()
    }

    @PostMapping("login")
    fun login(@RequestBody request: ApiModelLoginPost): ApiModelLoginGet {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))
        return ApiModelLoginGet(jwtUtils.generateToken(request.username))
    }
}