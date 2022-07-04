package com.dietrich.psiuapikt.controller.appointment.res

import com.dietrich.psiuapikt.model.appointment.AppointmentStatus
import java.time.LocalDateTime

data class AppointmentResponse(
    val start: LocalDateTime,
    val status: AppointmentStatus,
    val project: Project,
    val employee: Employee,
    val id: Long
) {
    data class Project(
        val name: String,
        val id: Long
    )

    data class Employee(
        val name: String,
        val id: Long
    )
}
