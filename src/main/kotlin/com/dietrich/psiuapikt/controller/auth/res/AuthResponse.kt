package com.dietrich.psiuapikt.controller.auth.res

import com.dietrich.psiuapikt.security.Token

data class AuthResponse(
    val user: UserData,
    val token: Token,
) {
    data class UserData(
        val name: String,
        val email: String,
        val role: String,
        val orgId: Long?,
        val projectId: Long?,
        val owner: Boolean,
        val id: Long
    )
}