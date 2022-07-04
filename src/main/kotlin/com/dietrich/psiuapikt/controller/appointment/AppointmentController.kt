package com.dietrich.psiuapikt.controller.appointment

import com.dietrich.psiuapikt.controller.appointment.req.OnDemandAppointmentRequest
import com.dietrich.psiuapikt.controller.appointment.res.AppointmentDetailResponse
import com.dietrich.psiuapikt.controller.appointment.res.AppointmentResponse
import com.dietrich.psiuapikt.exception.APIException
import com.dietrich.psiuapikt.security.CustomUserDetails
import com.dietrich.psiuapikt.service.appointment.AppointmentService
import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("appointments")
class AppointmentController(
    val appointmentService: AppointmentService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<AppointmentResponse> {
        return appointmentService.findAll().map {
            AppointmentResponse(
                it.start,
                it.status,
                AppointmentResponse.Project(
                    it.project.name,
                    it.project.id
                ),
                AppointmentResponse.Employee(
                    it.employee.user.name,
                    it.employee.id
                ),
                it.id
            )
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: Long): AppointmentDetailResponse {
        return appointmentService.find(id).let {
            AppointmentDetailResponse(
                it.start,
                it.status,
                AppointmentDetailResponse.Project(
                    it.project.name,
                    it.project.active,
                    it.project.id
                ),
                AppointmentDetailResponse.Employee(
                    it.employee.user.name,
                    it.employee.user.email,
                    it.employee.averageRating,
                    it.employee.user.active,
                    it.employee.id
                ),
                AppointmentDetailResponse.User(
                    it.user.name,
                    it.user.email,
                    it.user.active,
                    it.user.id
                ),
                it.id
            )
        }
    }


    //TODO CHECK IF EMPLOYEE IS ONLINE BEFORE REQUESTING APPOINTMENT
    //TODO CHECK PROJECTS TYPE (SHOULD BE ON_DEMAND)
    @PostMapping("request")
    fun request(@RequestBody request: OnDemandAppointmentRequest, @AuthenticationPrincipal principal: CustomUserDetails?): AppointmentDetailResponse {
        principal ?: throw APIException("User must be logged in") //TODO CREATE NEW EXCEPTION
        return appointmentService.requestOnDemand(request, principal).let {
            AppointmentDetailResponse(
                it.start,
                it.status,
                AppointmentDetailResponse.Project(
                    it.project.name,
                    it.project.active,
                    it.project.id
                ),
                AppointmentDetailResponse.Employee(
                    it.employee.user.name,
                    it.employee.user.email,
                    it.employee.averageRating,
                    it.employee.user.active,
                    it.employee.id
                ),
                AppointmentDetailResponse.User(
                    it.user.name,
                    it.user.email,
                    it.user.active,
                    it.user.id
                ),
                it.id
            )
        }
    }
}