package com.dietrich.psiuapikt.service.auth

import com.dietrich.psiuapikt.controller.auth.req.SignInRequest
import com.dietrich.psiuapikt.controller.auth.req.SignUpRequest
import com.dietrich.psiuapikt.controller.auth.res.AuthResponse
import com.dietrich.psiuapikt.model.user.Role
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.security.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder,
    val tokenProvider: JwtTokenProvider,
    val userRepository: UserRepository
) {
    fun signIn(request: SignInRequest) = authenticate(request.email, request.password)

    fun signUp(request: SignUpRequest): AuthResponse {
        val user = User(
            request.name,
            request.email,
            passwordEncoder.encode(request.password),
            Role.USER
        )

        userRepository.save(user)

        return authenticate(request.email, request.password)
    }

    private fun authenticate(email: String, password: String): AuthResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val token = tokenProvider.generateToken(authentication)
        return AuthResponse(token)
    }
}