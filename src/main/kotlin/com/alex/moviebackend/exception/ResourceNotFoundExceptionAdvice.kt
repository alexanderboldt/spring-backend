package com.alex.moviebackend.exception

import com.alex.moviebackend.exception.model.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ResourceNotFoundExceptionAdvice {

    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleException(exception: ResourceNotFoundException) = ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.message)
}