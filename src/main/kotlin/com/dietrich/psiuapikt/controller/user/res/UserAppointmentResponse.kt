package com.dietrich.psiuapikt.controller.user.res

import com.dietrich.psiuapikt.model.appointment.AppointmentStatus
import java.time.LocalDateTime

data class UserAppointmentResponse(
    val start: LocalDateTime,
    val status: AppointmentStatus,
    val rating: Double?,
    val employee: Employee,
    val project: Project,
    val id: Long
) {
    data class Project(
        val name: String,
        val active: Boolean,
        val id: Long
    )

    data class Employee(
        val name: String,
        val email: String,
        val active: Boolean,
        val id: Long
    )
}
