package com.alex.moviebackend.exception

import com.alex.moviebackend.repository.api.ApiModelErrorGet
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UnauthorizedExceptionAdvice {

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    fun handleException(exception: UnauthorizedException) = ApiModelErrorGet(HttpStatus.UNAUTHORIZED.value(), exception.message)
}