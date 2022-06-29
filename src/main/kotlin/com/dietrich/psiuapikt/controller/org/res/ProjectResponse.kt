package com.dietrich.psiuapikt.controller.org.res

import com.dietrich.psiuapikt.model.org.ProjectType

data class ProjectResponse(
    val name: String,
    val type: ProjectType,
    val org: Org,
    val active: Boolean,
    val id: Long
) {
    data class Org(
        val name: String,
        val active: Boolean,
        val id: Long,
    )
}
