package com.alex.moviebackend.exception

import com.alex.moviebackend.repository.api.ApiModelErrorGet
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BadRequestAdvice {

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleException() = ApiModelErrorGet(HttpStatus.BAD_REQUEST.value(), "Bad Request")
}