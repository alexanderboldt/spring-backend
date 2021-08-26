package com.alex.notesbackend

import com.alex.notesbackend.converter.SortConverter
import com.alex.notesbackend.interceptor.AuthorizationInterceptor
import com.alex.notesbackend.interceptor.SessionInterceptor
import com.alex.notesbackend.repository.session.SessionDao
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class ApplicationConfig(private val sessionDao: SessionDao) : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        super.addFormatters(registry)
        registry.apply {
            addConverter(SortConverter())
        }
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
        registry.apply {
            addInterceptor(AuthorizationInterceptor())
            addInterceptor(SessionInterceptor(sessionDao)).excludePathPatterns("/v1/login/**")
        }
    }
}