package com.dietrich.psiuapikt.controller.auth.req

data class SignUpRequest(
    val name: String,
    val email: String,
    val password: String
)