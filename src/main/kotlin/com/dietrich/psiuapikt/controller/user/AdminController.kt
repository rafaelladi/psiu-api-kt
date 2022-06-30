package com.dietrich.psiuapikt.controller.user

import com.dietrich.psiuapikt.controller.user.req.AdminUpdateRequest
import com.dietrich.psiuapikt.controller.user.res.AdminResponse
import com.dietrich.psiuapikt.service.user.AdminService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("admins")
class AdminController(
    val adminService: AdminService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<AdminResponse> {
        return adminService.findAll().map {
            AdminResponse(
                it.user.name,
                it.user.email,
                AdminResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.owner,
                it.user.active,
                it.id
            )
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: Long): AdminResponse {
        return adminService.find(id).let {
            AdminResponse(
                it.user.name,
                it.user.email,
                AdminResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.owner,
                it.user.active,
                it.id
            )
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, request: AdminUpdateRequest): AdminResponse {
        return adminService.update(id, request).let {
            AdminResponse(
                it.user.name,
                it.user.email,
                AdminResponse.Org(
                    it.org.name,
                    it.org.active,
                    it.org.id
                ),
                it.owner,
                it.user.active,
                it.id
            )
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        adminService.delete(id)
    }

}