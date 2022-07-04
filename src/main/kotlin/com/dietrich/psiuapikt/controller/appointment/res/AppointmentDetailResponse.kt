package com.dietrich.psiuapikt.controller.appointment.res

import com.dietrich.psiuapikt.model.appointment.AppointmentStatus
import java.time.LocalDateTime

class AppointmentDetailResponse(
    val start: LocalDateTime,
    val status: AppointmentStatus,
    val project: Project,
    val employee: Employee,
    val user: User,
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
        val averageRating: Double,
        val active: Boolean,
        val id: Long
    )

    data class User(
        val name: String,
        val email: String,
        val active: Boolean,
        val id: Long
    )
}
