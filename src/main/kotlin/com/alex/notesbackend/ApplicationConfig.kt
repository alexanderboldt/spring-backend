package com.alex.notesbackend

import com.alex.notesbackend.interceptor.AuthorizationInterceptor
import com.alex.notesbackend.interceptor.SessionInterceptor
import com.alex.notesbackend.repository.session.SessionDao
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfig(private val sessionDao: SessionDao) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.addInterceptor(AuthorizationInterceptor())
        registry.addInterceptor(SessionInterceptor(sessionDao))
    }
}