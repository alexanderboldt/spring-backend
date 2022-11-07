package com.alex.moviebackend.filter

import com.alex.moviebackend.repository.database.user.UserRepository
import com.alex.moviebackend.repository.database.user.UserService
import com.alex.moviebackend.util.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {

    private val bearerPrefix = "Bearer "

    // ----------------------------------------------------------------------------

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)
        val token = authorization.drop(bearerPrefix.length)

        if (authorization != null && authorization.startsWith(bearerPrefix) && jwtUtils.isTokenValid(token)) {
            val username = jwtUtils.getUsername(token)!!

            val userDetails = userService.loadUserByUsername(username)

            request.setAttribute("userId", userRepository.findByUsername(username)!!.id)

            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}