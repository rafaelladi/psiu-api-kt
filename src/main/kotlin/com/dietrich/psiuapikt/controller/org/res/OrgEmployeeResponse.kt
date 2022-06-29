package com.dietrich.psiuapikt.controller.org.res

data class OrgEmployeeResponse(
    val name: String,
    val email: String,
    val averageRating: Double,
    val id: Long,
    val project: Project? = null,
) {
    data class Project(
        val name: String,
        val active: Boolean,
        val id: Long
    )
}