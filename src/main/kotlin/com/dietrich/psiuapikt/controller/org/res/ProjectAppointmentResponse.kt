package com.dietrich.psiuapikt.controller.org.res

import com.dietrich.psiuapikt.model.appointment.AppointmentStatus
import java.time.LocalDateTime

data class ProjectAppointmentResponse(
    val start: LocalDateTime,
    val status: AppointmentStatus,
    val employee: Employee,
    val id: Long
) {
    data class Employee(
        val name: String,
        val id: Long
    )
}
