package com.dietrich.psiuapikt.config

import com.dietrich.psiuapikt.security.CustomUserDetailsService
import com.dietrich.psiuapikt.security.JwtAuthenticationEntryPoint
import com.dietrich.psiuapikt.security.JwtAuthenticationFilter
import com.dietrich.psiuapikt.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val tokenProvider: JwtTokenProvider,
    private val userDetailsService: CustomUserDetailsService,
    private val authenticationEntryPoint: JwtAuthenticationEntryPoint
) {
   @Bean
   fun filterChain(http: HttpSecurity): SecurityFilterChain {
       return http
           .cors().and()
           .csrf().disable()
           .exceptionHandling()
           .authenticationEntryPoint(authenticationEntryPoint)
           .and()
           .sessionManagement()
           .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
           .and()
           .authorizeRequests()
           .antMatchers("/auth/**").permitAll()
           .antMatchers("/swagger-ui.html").permitAll()
           .antMatchers("/swagger-ui/**").permitAll()
           .antMatchers("/api-docs/**").permitAll()
           .anyRequest().authenticated()
           .and()
           .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
           .build()
   }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object: WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**").allowedOrigins("*")
            }
        }
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(tokenProvider, userDetailsService)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }
}