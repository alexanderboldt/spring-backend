package com.alex.moviebackend.util

import com.alex.moviebackend.repository.api.ApiModelErrorGet
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletResponse

fun HttpServletResponse.error(status: HttpStatus, message: String) {
    addHeader(HttpHeaders.CONTENT_TYPE, "application/json")
    this.status = status.value()
    outputStream.write(ObjectMapper().writeValueAsBytes(ApiModelErrorGet(HttpStatus.UNAUTHORIZED.value(), message)))
}