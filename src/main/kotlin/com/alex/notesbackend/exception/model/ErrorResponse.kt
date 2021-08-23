package com.alex.notesbackend.exception.model

import org.springframework.http.HttpStatus

data class ErrorResponse(val status: Int, val message: String)