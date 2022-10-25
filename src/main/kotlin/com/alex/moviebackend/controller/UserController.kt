package com.alex.moviebackend.controller

import com.alex.moviebackend.exception.ResourceNotFoundException
import com.alex.moviebackend.repository.api.user.ApiModelUserGet
import com.alex.moviebackend.repository.api.user.ApiModelUserPost
import com.alex.moviebackend.repository.database.user.DbModelUser
import com.alex.moviebackend.repository.database.user.UserRepository
import com.alex.moviebackend.repository.mapping.toApiModelGet
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userRepository: UserRepository) {

    private val textUserNotFoundWithGivenId = "User with the given id not found!"

    // create

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postUser(@RequestBody request: ApiModelUserPost): ApiModelUserGet {
        return userRepository
            .save(DbModelUser(0, request.username, request.password, Date().time))
            .toApiModelGet()
    }

    // read

    @GetMapping
    fun getAllUsers(): List<ApiModelUserGet> = userRepository.findAll().toApiModelGet()

    // delete

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable("id") id: Long) {
        userRepository.findByIdOrNull(id) ?: throw ResourceNotFoundException(textUserNotFoundWithGivenId)
        userRepository.deleteById(id)
    }
}