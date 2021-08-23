package com.alex.notesbackend.interceptor

import com.alex.notesbackend.exception.UnauthorizedException
import com.alex.notesbackend.repository.session.SessionDao
import com.alex.notesbackend.utils.getBearerToken
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SessionInterceptor(private val sessionDao: SessionDao) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURL.contains("login")) return true

        request
            .getHeader("Authorization")
            ?.let { token -> sessionDao.findByToken(token.getBearerToken()) }
            ?.let { session -> request.setAttribute("user_id", session.userId) }
            ?: throw UnauthorizedException("User not found")

        return true
    }
}