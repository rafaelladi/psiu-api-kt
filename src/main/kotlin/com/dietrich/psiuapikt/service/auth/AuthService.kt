package com.dietrich.psiuapikt.service.auth

import com.dietrich.psiuapikt.controller.auth.req.SignInRequest
import com.dietrich.psiuapikt.controller.auth.req.SignUpRequest
import com.dietrich.psiuapikt.controller.auth.res.AuthResponse
import com.dietrich.psiuapikt.model.user.Role
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.user.AdminRepository
import com.dietrich.psiuapikt.repository.user.EmployeeRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.security.JwtTokenProvider
import org.springframework.data.repository.findByIdOrNull
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
    val userRepository: UserRepository,
    val adminRepository: AdminRepository,
    val employeeRepository: EmployeeRepository
) {
    fun signIn(request: SignInRequest) = authenticate(request.email, request.password)

    fun signUp(request: SignUpRequest): AuthResponse {
        val user = User(
            request.name,
            request.email,
            passwordEncoder.encode(request.password),
            Role.USER
        )
        //TODO CHECK IF EMAIL IS ALREADY REGISTERED
        userRepository.save(user)

        return authenticate(request.email, request.password)
    }

    private fun authenticate(email: String, password: String): AuthResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(email, password)
        )

        SecurityContextHolder.getContext().authentication = authentication

        val userData = getUserData(email)
        val token = tokenProvider.generateToken(userData)
        return AuthResponse(
            userData,
            token
        )
    }

    fun getUserData(email: String): AuthResponse.UserData {
        val user = userRepository.findByEmail(email)!!
        val admin = adminRepository.findByIdOrNull(user.id)
        val employee = employeeRepository.findByIdOrNull(user.id)
        return AuthResponse.UserData(
            user.name,
            user.email,
            user.role.name,
            admin?.org?.id,
            employee?.project?.id,
            admin?.owner ?: false,
            user.id
        )
    }

    fun refreshToken(refreshToken: String): AuthResponse {
        val username = tokenProvider.getUsernameFromToken(refreshToken)
        val userData = getUserData(username)
        val token = tokenProvider.generateToken(userData)

        return AuthResponse(
            userData,
            token
        )
    }
}