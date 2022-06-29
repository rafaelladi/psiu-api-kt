package com.dietrich.psiuapikt.controller.org.req

data class RegisterOrgRequest(
    val name: String,
    val description: String,
    val ownerName: String,
    val ownerEmail: String,
    val ownerPassword: String
)