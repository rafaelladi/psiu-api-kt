package com.dietrich.psiuapikt.controller.user.res

data class AdminResponse(
    val name: String,
    val email: String,
    val org: Org,
    val owner: Boolean,
    val active: Boolean,
    val id: Long
) {
    data class Org(
        val name: String,
        val active: Boolean,
        val id: Long
    )
}
