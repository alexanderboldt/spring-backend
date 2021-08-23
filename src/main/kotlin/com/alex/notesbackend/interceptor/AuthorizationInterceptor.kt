package com.alex.notesbackend.interceptor

import com.alex.notesbackend.exception.UnauthorizedException
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationInterceptor : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val hash = "e4bbe5b7a4c1eb55652965aee885dd59bd2ee7f4"

        return request
            .getHeader("Client-Secret")
            ?.let { if (it == hash) true else null }
            ?: throw UnauthorizedException()
    }
}