package com.alex.moviebackend.filter

import com.alex.moviebackend.repository.api.ApiModelErrorGet
import com.alex.moviebackend.util.HEADER_CLIENT_SECRET
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper

@Component
class AuthorizationFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        request as HttpServletRequest
        response as HttpServletResponse

        val clientSecret = "e4bbe5b7a4c1eb55652965aee885dd59bd2ee7f4"

        when (request.getHeader(HEADER_CLIENT_SECRET) == clientSecret) {
            true -> chain.doFilter(request, response)
            false -> {
                HttpServletResponseWrapper(response).apply {
                    resetBuffer()
                    addHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    status = HttpStatus.UNAUTHORIZED.value()
                    outputStream.write(
                        ObjectMapper()
                            .writeValueAsBytes(ApiModelErrorGet(HttpStatus.UNAUTHORIZED.value(), "Client-Secret is missing!"))
                    )
                }
            }
        }
    }
}