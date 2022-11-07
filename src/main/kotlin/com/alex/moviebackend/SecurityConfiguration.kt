package com.alex.moviebackend

import com.alex.moviebackend.exception.JwtAuthenticationEntryPoint
import com.alex.moviebackend.filter.ClientSecretAuthorizationFilter
import com.alex.moviebackend.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration(
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val clientSecretAuthorizationFilter: ClientSecretAuthorizationFilter,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    @Bean
    fun getPasswordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun getAuthenticationManager(authenticationConfiguration: AuthenticationConfiguration) = authenticationConfiguration.authenticationManager

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .cors().and().csrf().disable()
            .exceptionHandling {
                it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
            }.sessionManagement() {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.authorizeRequests {
                it
                    .antMatchers("/api/v1/register").permitAll()
                    .antMatchers("/api/v1/login").permitAll()
                    .anyRequest().authenticated()
            }

        httpSecurity.addFilterBefore(clientSecretAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, ClientSecretAuthorizationFilter::class.java)

        return httpSecurity.build()
    }
}