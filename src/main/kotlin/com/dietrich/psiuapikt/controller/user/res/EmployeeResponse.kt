package com.dietrich.psiuapikt.controller.user.res

data class EmployeeResponse(
    val name: String,
    val email: String,
    val project: Project?,
    val org: Org,
    val active: Boolean,
    val id: Long
) {
    data class Project(
        val name: String,
        val active: Boolean,
        val id: Long
    )

    data class Org(
        val name: String,
        val active: Boolean,
        val id: Long
    )
}
