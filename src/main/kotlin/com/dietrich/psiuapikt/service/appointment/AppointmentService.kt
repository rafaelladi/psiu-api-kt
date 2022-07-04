package com.dietrich.psiuapikt.service.appointment

import com.dietrich.psiuapikt.controller.appointment.req.OnDemandAppointmentRequest
import com.dietrich.psiuapikt.exception.APIException
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.appointment.Appointment
import com.dietrich.psiuapikt.model.appointment.AppointmentStatus
import com.dietrich.psiuapikt.model.org.ProjectType
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.appointment.AppointmentRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.security.CustomUserDetails
import com.dietrich.psiuapikt.service.org.OrgService
import com.dietrich.psiuapikt.service.org.ProjectService
import com.dietrich.psiuapikt.service.user.EmployeeService
import com.dietrich.psiuapikt.service.user.UserService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Principal
import java.time.LocalDateTime

@Service
class AppointmentService(
    val appointmentRepository: AppointmentRepository,
    val orgService: OrgService,
    val projectService: ProjectService,
    val employeeService: EmployeeService,
    val userService: UserService
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

    fun findAll(): List<Appointment> {
        return appointmentRepository.findAll()
    }

    fun find(id: Long): Appointment {
        return appointmentRepository.findByIdOrNull(id) ?: throw NotFoundException("Appointment", "id", id)
    }

    fun requestOnDemand(request: OnDemandAppointmentRequest, principal: CustomUserDetails): Appointment {
        val user = userService.find(principal.id)
        val employee = employeeService.find(request.employeeId)
        val project = employee.project ?: throw APIException("Employee is not currently in a project") //TODO CREATE NEW EXCEPTION
        if(project.type != ProjectType.ON_DEMAND) throw APIException("Project is not of type ON_DEMAND") //TODO CREATE NEW EXCEPTION
        val appointment = appointmentRepository.save(Appointment(
            LocalDateTime.now(),
            AppointmentStatus.REQUESTED,
            project,
            employee,
            user
        ))

        //TODO NOTIFY EMPLOYEE OF THE REQUEST

        return appointment
    }
}