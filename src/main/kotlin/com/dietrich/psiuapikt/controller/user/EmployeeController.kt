package com.dietrich.psiuapikt.controller.user

import com.dietrich.psiuapikt.controller.user.res.EmployeeResponse
import com.dietrich.psiuapikt.service.user.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("employees")
class EmployeeController(
    val employeeService: EmployeeService
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


}