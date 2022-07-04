package com.dietrich.psiuapikt.security

class TokenUser(
    val email: String,
    val orgId: Long?,
    val projectId: Long?,
    val role: String,
    val id: Long
)