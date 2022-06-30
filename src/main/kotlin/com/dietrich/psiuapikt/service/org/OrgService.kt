package com.dietrich.psiuapikt.service.org

import com.dietrich.psiuapikt.controller.org.req.OrgUpdateRequest
import com.dietrich.psiuapikt.controller.org.req.RegisterOrgRequest
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.org.Org
import com.dietrich.psiuapikt.model.user.Admin
import com.dietrich.psiuapikt.model.user.Role
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.org.OrgRepository
import com.dietrich.psiuapikt.repository.user.AdminRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.service.util.MergeService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrgService(
    val orgRepository: OrgRepository,
    val adminRepository: AdminRepository,
    val userRepository: UserRepository,
    val mergeService: MergeService
) {
    fun findAll(): List<Org> {
        return orgRepository.findAll()
    }

    fun register(request: RegisterOrgRequest): Org {
        var user = User(
            request.ownerName,
            request.ownerEmail,
            request.ownerPassword,
            Role.ADMIN
        )
        user = userRepository.saveAndFlush(user)

        var org = Org(
            request.name,
            request.description,
        )
        org = orgRepository.saveAndFlush(org)

        var admin = Admin(
            user,
            org,
            user.id
        )
        admin = adminRepository.saveAndFlush(admin)

        org.owner = admin
        org.admins += admin
        return orgRepository.save(org)
    }

    fun update(id: Long, request: OrgUpdateRequest): Org {
        val org = find(id)
        mergeService.merge(request, org)

        return orgRepository.save(org)
    }

    fun delete(id: Long) {
        val org = find(id)
        org.active = false
        orgRepository.save(org)
    }

    fun find(id: Long): Org = orgRepository.findByIdOrNull(id) ?:
        throw NotFoundException("Org", "id", id)

    fun exists(id: Long) {
        if(!orgRepository.existsById(id))
            throw NotFoundException("Org", "id", id)
    }
}