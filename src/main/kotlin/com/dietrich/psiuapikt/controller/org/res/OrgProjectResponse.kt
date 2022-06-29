package com.dietrich.psiuapikt.controller.org.res

import com.dietrich.psiuapikt.model.org.ProjectType

data class OrgProjectResponse(
    val name: String,
    val type: ProjectType,
    val active: Boolean,
    val id: Long
)
