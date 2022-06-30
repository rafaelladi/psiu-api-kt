package com.dietrich.psiuapikt.repository.appointment

import com.dietrich.psiuapikt.model.appointment.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppointmentRepository : JpaRepository<Appointment, Long> {
    fun findByProjectOrgId(id: Long): List<Appointment>
    fun findByProjectId(id: Long): List<Appointment>
    fun findByEmployeeId(id: Long): List<Appointment>
}