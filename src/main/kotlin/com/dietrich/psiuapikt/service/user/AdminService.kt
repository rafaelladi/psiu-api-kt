package com.dietrich.psiuapikt.service.user

import com.dietrich.psiuapikt.controller.org.req.OrgAdminRequest
import com.dietrich.psiuapikt.controller.user.req.AdminUpdateRequest
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.user.Admin
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.user.AdminRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.service.org.OrgService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminService(
    val adminRepository: AdminRepository,
    val orgService: OrgService,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder
) {
    fun findAll(): List<Admin> {
        return adminRepository.findAll()
    }

    fun findByOrg(id: Long): List<Admin> {
        orgService.exists(id)
        return adminRepository.findByOrgId(id)
    }

    fun registerAdmin(id: Long, request: OrgAdminRequest): Admin {
        val org = orgService.find(id)
        //TODO ADD ROLES
        val user = User(
            request.name,
            request.email,
            passwordEncoder.encode(request.password)
        )
        userRepository.saveAndFlush(user)

        val admin = Admin(
            user,
            org,
            user.id
        )
        adminRepository.saveAndFlush(admin)

        return admin
    }

    fun find(id: Long): Admin {
        return adminRepository.findByIdOrNull(id) ?: throw NotFoundException("Admin", "id", id)
    }

    fun update(id: Long, request: AdminUpdateRequest): Admin {
        val admin = find(id)
        request.name?.let {
            admin.user.name = it
        }

        return adminRepository.save(admin)
    }

    fun delete(id: Long) {
        val admin = find(id)
        admin.user.active = false
        adminRepository.save(admin)
    }
}