package com.dietrich.psiuapikt.controller.user

import com.dietrich.psiuapikt.controller.user.req.UserUpdateRequest
import com.dietrich.psiuapikt.controller.user.res.UserAppointmentResponse
import com.dietrich.psiuapikt.controller.user.res.UserResponse
import com.dietrich.psiuapikt.service.appointment.AppointmentService
import com.dietrich.psiuapikt.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController(
    val userService: UserService,
    val appointmentService: AppointmentService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<UserResponse> {
        return userService.findAll().map {
            UserResponse(
                it.name,
                it.email,
                it.id
            )
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: Long): UserResponse {
        return userService.find(id).let {
            UserResponse(
                it.name,
                it.email,
                it.id
            )
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, @RequestBody request: UserUpdateRequest): UserResponse {
        return userService.update(id, request).let {
            UserResponse(
                it.name,
                it.email,
                it.id
            )
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        return userService.delete(id)
    }

    @GetMapping("{id}/appointments")
    fun appointments(@PathVariable id: Long): List<UserAppointmentResponse> {
        return appointmentService.findByUser(id).map {
            UserAppointmentResponse(
                it.start,
                it.status,
                it.rating,
                UserAppointmentResponse.Employee(
                    it.employee.user.name,
                    it.employee.user.email,
                    it.employee.user.active,
                    it.employee.id
                ),
                UserAppointmentResponse.Project(
                    it.project.name,
                    it.project.active,
                    it.project.id
                ),
                it.id
            )
        }
    }

}