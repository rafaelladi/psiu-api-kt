package com.dietrich.psiuapikt.service.user

import com.dietrich.psiuapikt.controller.org.req.OrgEmployeeRequest
import com.dietrich.psiuapikt.controller.user.req.EmployeeUpdateRequest
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.user.Employee
import com.dietrich.psiuapikt.model.user.Role
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.user.EmployeeRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.service.org.OrgService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class EmployeeService(
    val employeeRepository: EmployeeRepository,
    val orgService: OrgService,
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository
) {
    fun findAll(): List<Employee> {
        return employeeRepository.findAll()
    }

    fun findByOrg(id: Long): List<Employee> {
        orgService.exists(id)
        return employeeRepository.findByOrgId(id)
    }

    fun registerEmployee(id: Long, request: OrgEmployeeRequest): Employee {
        val org = orgService.find(id)
        //TODO ADD ROLES
        val user = User(
            request.name,
            request.email,
            passwordEncoder.encode(request.password),
            Role.EMPLOYEE
        )
        userRepository.saveAndFlush(user)

        val employee = Employee(
            user,
            org
        )
        employeeRepository.saveAndFlush(employee)

        return employee
    }

    fun findByProject(id: Long): List<Employee> {
        return employeeRepository.findByProjectId(id)
    }

    fun find(id: Long): Employee {
        return employeeRepository.findByIdOrNull(id) ?: throw NotFoundException("Employee", "id", id)
    }

    fun update(id: Long, request: EmployeeUpdateRequest): Employee {
        val employee = find(id)
        request.name?.let {
            employee.user.name = request.name
        }

        return employeeRepository.save(employee)
    }

    fun delete(id: Long) {
        val employee = find(id)
        employee.user.active = false
        employeeRepository.save(employee)
    }
}