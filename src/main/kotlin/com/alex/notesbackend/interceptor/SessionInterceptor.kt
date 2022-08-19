package com.alex.notesbackend.interceptor

import com.alex.notesbackend.exception.UnauthorizedException
import com.alex.notesbackend.repository.session.SessionDao
import com.alex.notesbackend.utils.ATTRIBUTE_USER_ID
import org.springframework.http.HttpHeaders
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

class SessionInterceptor(private val sessionDao: SessionDao) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        request
            .getHeader(HttpHeaders.AUTHORIZATION)
            .split(" ")
            .let { it[0] to it[1] }
            .let { (type, token) -> sessionDao.findByTypeAndToken(type, token) }
            ?.let { session -> request.setAttribute(ATTRIBUTE_USER_ID, session.userId) }
            ?: throw UnauthorizedException("Invalid session!")

        return true
    }
}