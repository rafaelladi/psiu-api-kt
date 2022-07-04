package com.dietrich.psiuapikt.controller.auth

import com.dietrich.psiuapikt.controller.auth.req.SignInRequest
import com.dietrich.psiuapikt.controller.auth.req.SignUpRequest
import com.dietrich.psiuapikt.controller.auth.res.AuthResponse
import com.dietrich.psiuapikt.service.auth.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auth")
class AuthController(
    val authService: AuthService
) {
    @PostMapping("sign-in")
    fun signIn(@RequestBody request: SignInRequest): AuthResponse {
        return authService.signIn(request)
    }

    @PostMapping("sign-up")
    fun signUp(@RequestBody request: SignUpRequest): AuthResponse {
        return authService.signUp(request)
    }
}