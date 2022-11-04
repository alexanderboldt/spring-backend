package com.alex.moviebackend.util

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit

@Component
class JwtUtils(@Value("\${app.secret.jwt}") val secret: String) {

    fun generateToken(username: String): String {
        val now = Instant.now()

        return Jwts
            .builder()
            .setSubject(username)
            .setIssuer("Movie-Backend")
            .setIssuedAt(now.toDate())
            .setExpiration(now.plus(7, ChronoUnit.DAYS).toDate())
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        return try {
            Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
            true
        } catch (throwable: Throwable) {
            println(throwable)
            false
        }
    }

    fun getUsername(token: String): String? {
        return try {
            Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
                .body
                .subject
        } catch (throwable: Throwable) {
            println(throwable)
            null
        }
    }
}