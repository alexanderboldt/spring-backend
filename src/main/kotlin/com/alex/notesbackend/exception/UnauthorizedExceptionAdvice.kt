package com.alex.notesbackend.exception

import com.alex.notesbackend.exception.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UnauthorizedExceptionAdvice {

    @ExceptionHandler(UnauthorizedException::class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    fun handleException(exception: UnauthorizedException) = ErrorResponse(HttpStatus.UNAUTHORIZED.value(), exception.message)
}