package com.dietrich.psiuapikt.controller.auth.res

data class AuthResponse(
    val token: String,
    val tokenType: String = "Bearer"
)