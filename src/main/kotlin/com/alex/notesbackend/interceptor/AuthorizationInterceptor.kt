package com.alex.notesbackend.interceptor

import com.alex.notesbackend.exception.UnauthorizedException
import com.alex.notesbackend.utils.HEADER_CLIENT_SECRET
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationInterceptor : HandlerInterceptor {

    private val clientSecret = "e4bbe5b7a4c1eb55652965aee885dd59bd2ee7f4"

    // ----------------------------------------------------------------------------

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        when (request.getHeader(HEADER_CLIENT_SECRET) == clientSecret) {
            true -> return true
            else -> throw UnauthorizedException("Client-Secret missing")
        }
    }
}