package com.dietrich.psiuapikt.controller.user

import com.dietrich.psiuapikt.controller.user.req.EmployeeUpdateRequest
import com.dietrich.psiuapikt.controller.user.res.EmployeeAppointmentResponse
import com.dietrich.psiuapikt.controller.user.res.EmployeeResponse
import com.dietrich.psiuapikt.service.appointment.AppointmentService
import com.dietrich.psiuapikt.service.user.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("employees")
class EmployeeController(
    val employeeService: EmployeeService,
    val appointmentService: AppointmentService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<EmployeeResponse>   {
        return employeeService.findAll().map {
            EmployeeResponse(
                it.user.name,
                it.user.email,
                it.project?.let {p ->
                    EmployeeResponse.Project(
                        p.name,
                        p.active,
                        p.id
                    )
                },
                EmployeeResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.user.active,
                it.id
            )
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: Long): EmployeeResponse {
        return employeeService.find(id).let {
            EmployeeResponse(
                it.user.name,
                it.user.email,
                it.project?.let {p ->
                    EmployeeResponse.Project(
                        p.name,
                        p.active,
                        p.id
                    )
                },
                EmployeeResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.user.active,
                it.id
            )
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, @RequestBody request: EmployeeUpdateRequest): EmployeeResponse {
        return employeeService.update(id, request).let {
            EmployeeResponse(
                it.user.name,
                it.user.email,
                it.project?.let {p ->
                    EmployeeResponse.Project(
                        p.name,
                        p.active,
                        p.id
                    )
                },
                EmployeeResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.user.active,
                it.id
            )
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        employeeService.delete(id)
    }

    @GetMapping("{id}/appointments")
    @ResponseStatus(HttpStatus.OK)
    fun appointments(@PathVariable id: Long): List<EmployeeAppointmentResponse> {
        return appointmentService.findByEmployee(id).map {
            EmployeeAppointmentResponse(
                it.start,
                it.status,
                it.rating,
                EmployeeAppointmentResponse.Project(
                    it.project.name,
                    it.project.active,
                    it.project.id
                ),
                it.id
            )
        }
    }
}