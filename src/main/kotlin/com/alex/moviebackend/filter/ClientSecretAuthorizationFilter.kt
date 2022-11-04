package com.alex.moviebackend.filter

import com.alex.moviebackend.util.HEADER_CLIENT_SECRET
import com.alex.moviebackend.util.error
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ClientSecretAuthorizationFilter(@Value("\${app.secret.client}") val secret: String) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        when (request.getHeader(HEADER_CLIENT_SECRET) == secret) {
            true -> filterChain.doFilter(request, response)
            false -> response.error(HttpStatus.UNAUTHORIZED, "Client-Secret is missing!")
        }
    }
}