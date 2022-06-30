package com.dietrich.psiuapikt.service.appointment

import com.dietrich.psiuapikt.model.appointment.Appointment
import com.dietrich.psiuapikt.repository.appointment.AppointmentRepository
import com.dietrich.psiuapikt.service.org.OrgService
import org.springframework.stereotype.Service

@Service
class AppointmentService(
    val appointmentRepository: AppointmentRepository,
    val orgService: OrgService
) {
    fun findByOrg(id: Long): List<Appointment> {
        orgService.exists(id)
        return appointmentRepository.findByProjectOrgId(id)
    }

    fun findByProject(id: Long): List<Appointment> {
        return appointmentRepository.findByProjectId(id)
    }

    fun findByEmployee(id: Long): List<Appointment> {
        return appointmentRepository.findByEmployeeId(id)
    }

    fun findByUser(id: Long): List<Appointment> {
        return appointmentRepository.findByUserId(id)
    }
}