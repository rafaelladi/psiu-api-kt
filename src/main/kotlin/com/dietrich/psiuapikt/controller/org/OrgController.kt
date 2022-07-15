package com.dietrich.psiuapikt.controller.org

import com.dietrich.psiuapikt.controller.org.req.*
import com.dietrich.psiuapikt.controller.org.res.*
import com.dietrich.psiuapikt.service.appointment.AppointmentService
import com.dietrich.psiuapikt.service.org.OrgService
import com.dietrich.psiuapikt.service.org.ProjectService
import com.dietrich.psiuapikt.service.user.AdminService
import com.dietrich.psiuapikt.service.user.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("orgs")
class OrgController(
    val orgService: OrgService,
    val appointmentService: AppointmentService,
    val employeeService: EmployeeService,
    val adminService: AdminService,
    val projectService: ProjectService
) {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findAll(): List<OrgResponse> {
        return orgService.findAll().map {
            OrgResponse(
                it.name,
                it.description,
                it.id
            )
        }
    }

    @GetMapping("requests")
    @ResponseStatus(HttpStatus.OK)
    fun findRequests(): List<OrgNotEvaluatedResponse> {
        return orgService.findRequests().map {
            OrgNotEvaluatedResponse(
                it.name,
                it.description,
                it.ownerName,
                it.ownerEmail,
                it.id
            )
        }
    }

    @PutMapping("requests/{id}/accept")
    @ResponseStatus(HttpStatus.OK)
    fun accept(@PathVariable id: Long, @RequestParam accepted: Boolean): Long? {
        return orgService.accept(id, accepted)
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    fun register(@RequestBody request: RegisterOrgRequest): OrgRegisterResponse {
        return orgService.register(request).let {
            OrgRegisterResponse(
                it.name,
                it.ownerName,
                it.ownerEmail
            )
        }
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: Long): OrgResponse {
        return orgService.find(id).let {
            OrgResponse(
                it.name,
                it.description,
                it.id
            )
        }
    }

    //TODO CHECK ACCESS FOR ALL THE ENDPOINTS BELOW
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    fun update(@PathVariable id: Long, @RequestBody request: OrgUpdateRequest): OrgResponse {
        return orgService.update(id, request).let {
            OrgResponse(
                it.name,
                it.description,
                it.id
            )
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        //TODO should projects be marked as inactive also?
        orgService.delete(id)
    }

    @GetMapping("{id}/appointments")
    @ResponseStatus(HttpStatus.OK)
    fun appointments(@PathVariable id: Long): List<OrgAppointmentResponse> {
        return appointmentService.findByOrg(id).map {
            OrgAppointmentResponse(
                it.start,
                it.status,
                OrgAppointmentResponse.Project(
                    it.project.name,
                    it.project.type,
                    it.project.active,
                    it.project.id
                ),
                OrgAppointmentResponse.Employee(
                    it.employee.user.name,
                    it.employee.id
                ),
                it.id
            )
        }
    }

    @GetMapping("{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    fun employees(@PathVariable id: Long): List<OrgEmployeeResponse> {
        return employeeService.findByOrg(id).map {
            OrgEmployeeResponse(
                it.user.name,
                it.user.email,
                it.averageRating,
                it.id,
                it.project?.let {project ->
                    OrgEmployeeResponse.Project(
                        project.name,
                        project.active,
                        project.id
                    )
                }
            )
        }
    }

    @PostMapping("{id}/employees")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerEmployee(@PathVariable id: Long, @RequestBody request: OrgEmployeeRequest): OrgEmployeeResponse {
        return employeeService.registerEmployee(id, request).let {
            OrgEmployeeResponse(
                it.user.name,
                it.user.email,
                it.averageRating,
                it.id
            )
        }
    }

    @GetMapping("{id}/admins")
    @ResponseStatus(HttpStatus.OK)
    fun admins(@PathVariable id: Long): List<OrgAdminResponse> {
        return adminService.findByOrg(id).map {
            OrgAdminResponse(
                it.user.name,
                it.user.email,
                it.id
            )
        }
    }

    @PostMapping("{id}/admins")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerAdmins(@PathVariable id: Long, @RequestBody request: OrgAdminRequest): OrgAdminResponse {
        return adminService.registerAdmin(id, request).let {
            OrgAdminResponse(
                it.user.name,
                it.user.email,
                it.id
            )
        }
    }

    @GetMapping("{id}/projects")
    @ResponseStatus(HttpStatus.OK)
    fun projects(@PathVariable id: Long): List<OrgProjectResponse> {
        return projectService.findByOrg(id).map {
            OrgProjectResponse(
                it.name,
                it.type,
                it.active,
                it.id
            )
        }
    }

    @PostMapping("{id}/projects")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerProjects(@PathVariable id: Long, @RequestBody request: OrgProjectRequest): OrgProjectResponse {
        return projectService.registerProject(id, request).let {
            OrgProjectResponse(
                it.name,
                it.type,
                it.active,
                it.id
            )
        }
    }
}