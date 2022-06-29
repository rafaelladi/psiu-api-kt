package com.dietrich.psiuapikt.controller.org.res

data class ProjectEmployeeResponse(
    val name: String,
    val email: String,
    val averageRating: Double,
    val org: Org,
    val id: Long
) {
    data class Org(
        val name: String,
        val id: Long
    )
}
