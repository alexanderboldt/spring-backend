package com.alex.moviebackend.interceptor

import com.alex.moviebackend.exception.UnauthorizedException
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val hash = "e4bbe5b7a4c1eb55652965aee885dd59bd2ee7f4"

        return when (request.getHeader("Client-Secret") == hash) {
            true -> true
            false -> throw UnauthorizedException("Client-Secret is missing!")
        }
    }
}