package com.dietrich.psiuapikt.service.org

import com.dietrich.psiuapikt.controller.org.req.OrgUpdateRequest
import com.dietrich.psiuapikt.controller.org.req.RegisterOrgRequest
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.org.Org
import com.dietrich.psiuapikt.model.org.Request
import com.dietrich.psiuapikt.model.org.RequestStatus
import com.dietrich.psiuapikt.model.user.Admin
import com.dietrich.psiuapikt.model.user.Role
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.org.OrgRepository
import com.dietrich.psiuapikt.repository.org.RequestRepository
import com.dietrich.psiuapikt.repository.user.AdminRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import com.dietrich.psiuapikt.service.util.MergeService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class OrgService(
    val orgRepository: OrgRepository,
    val requestRepository: RequestRepository,
    val adminRepository: AdminRepository,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val mergeService: MergeService
) {
    fun findAll(): List<Org> {
        return orgRepository.findAll()
    }

    fun register(registerOrgRequest: RegisterOrgRequest): Request {
        return requestRepository.saveAndFlush(Request(
            registerOrgRequest.name,
            registerOrgRequest.description,
            registerOrgRequest.ownerName,
            registerOrgRequest.ownerEmail,
            passwordEncoder.encode(registerOrgRequest.ownerPassword) //TODO ASK USER TO SETUP PASSWORD AFTER ORG IS ACCEPTED
        ))
    }

    fun accept(id: Long, accepted: Boolean): Long? {
        val request = requestRepository.findByIdOrNull(id) ?: throw NotFoundException("Request", "id", id)
        return if(!accepted) { //TODO NOTIFY OWNER
            request.status = RequestStatus.REJECTED
            null
        } else {
            request.status = RequestStatus.ACCEPTED
            val user = userRepository.save(User(
                request.ownerName,
                request.ownerEmail,
                request.ownerPassword,
                Role.ADMIN
            ))

            var org = orgRepository.save(Org(
                request.name,
                request.description,
            ))

            val admin = adminRepository.save(Admin(
                user,
                org,
                user.id
            ))

            org.owner = admin
            org.admins += admin
            org = orgRepository.save(org)
            org.id
        }
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

    fun findRequests(): List<Request> {
        return requestRepository.findAll()
    }
}