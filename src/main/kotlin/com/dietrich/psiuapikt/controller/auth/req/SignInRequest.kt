package com.dietrich.psiuapikt.controller.auth.req

data class SignInRequest(
    val email: String,
    val password: String
)