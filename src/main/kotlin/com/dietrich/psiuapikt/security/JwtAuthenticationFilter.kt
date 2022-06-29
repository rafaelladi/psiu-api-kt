package com.dietrich.psiuapikt.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    val tokenProvider: JwtTokenProvider,
    val userDetailsService: CustomUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = getJwtFromRequest(request)

        if(token != null && tokenProvider.validateToken(token)) {
            val username = tokenProvider.getUsernameFromToken(token)
            val userDetails = userDetailsService.loadUserByUsername(username)
            //TODO optimize so there is no need to query the user every request

            val authenticationToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            )

            authenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authenticationToken
        }

        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization") ?: ""
        if(bearerToken.isBlank() && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }

        return null
    }
}