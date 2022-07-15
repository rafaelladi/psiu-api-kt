package com.dietrich.psiuapikt.security

import com.dietrich.psiuapikt.model.user.Role
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class WebsocketRequestsFilter : OncePerRequestFilter() {
    @Value("\${app.api-key}")
    var apiKey: String = "12345"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if(validateRequest(request)) {
            val authentication = UsernamePasswordAuthenticationToken(
                null,
                null,
                listOf(SimpleGrantedAuthority(Role.CHAT_API.name))
            )
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } else {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "API Key not valid")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return "/messages" != request.servletPath && "/messages/" != request.servletPath
    }

    private fun validateRequest(request: HttpServletRequest): Boolean {
        val requestKey = request.getHeader("Authorization") ?: ""
        return requestKey == apiKey
    }
}