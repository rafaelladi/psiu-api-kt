package com.dietrich.psiuapikt.controller.org.res

import com.dietrich.psiuapikt.model.appointment.AppointmentStatus
import com.dietrich.psiuapikt.model.org.ProjectType
import java.time.LocalDateTime

data class OrgAppointmentResponse(
    val start: LocalDateTime,
    val status: AppointmentStatus,
    val project: Project,
    val employee: Employee,
    val id: Long
) {
    data class Employee(
        val name: String,
        val id: Long
    )

    data class Project(
        val name: String,
        val type: ProjectType,
        val active: Boolean,
        val id: Long
    )
}