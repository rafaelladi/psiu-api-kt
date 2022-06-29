package com.dietrich.psiuapikt.controller.org

import com.dietrich.psiuapikt.controller.org.req.ProjectUpdateRequest
import com.dietrich.psiuapikt.controller.org.res.ProjectAppointmentResponse
import com.dietrich.psiuapikt.controller.org.res.ProjectDetailResponse
import com.dietrich.psiuapikt.controller.org.res.ProjectEmployeeResponse
import com.dietrich.psiuapikt.controller.org.res.ProjectResponse
import com.dietrich.psiuapikt.service.appointment.AppointmentService
import com.dietrich.psiuapikt.service.org.ProjectService
import com.dietrich.psiuapikt.service.user.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("projects")
class ProjectController(
    val projectService: ProjectService,
    val employeeService: EmployeeService,
    val appointmentService: AppointmentService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<ProjectResponse> {
        return projectService.findAll().map {
            ProjectResponse(
                it.name,
                it.type,
                ProjectResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.active,
                it.id
            )
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: Long): ProjectDetailResponse {
        return projectService.find(id).let {
            ProjectDetailResponse(
                it.name,
                it.description,
                it.type,
                ProjectDetailResponse.Org(
                    it.org.name,
                    it.org.id
                ),
                it.active,
                it.id
            )
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, request: ProjectUpdateRequest): ProjectDetailResponse {
        return projectService.update(id, request).let {
            ProjectDetailResponse(
                it.name,
                it.description,
                it.type,
                ProjectDetailResponse.Org(
                    it.org.name,
                    it.org.id
                ),
                it.active,
                it.id
            )
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        projectService.delete(id)
    }

    @GetMapping("{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    fun employees(@PathVariable id: Long): List<ProjectEmployeeResponse> {
        return employeeService.findByProject(id).map {
            ProjectEmployeeResponse(
                it.user.name,
                it.user.email,
                it.averageRating,
                ProjectEmployeeResponse.Org(
                    it.org.name,
                    it.org.id
                ),
                it.id
            )
        }
    }

    @GetMapping("{id}/appointments")
    @ResponseStatus(HttpStatus.OK)
    fun appointments(@PathVariable id: Long): List<ProjectAppointmentResponse> {
        return appointmentService.findByProject(id).map {
            ProjectAppointmentResponse(
                it.start,
                it.status,
                ProjectAppointmentResponse.Employee(
                    it.employee.user.name,
                    it.employee.id
                ),
                it.id
            )
        }
    }
}