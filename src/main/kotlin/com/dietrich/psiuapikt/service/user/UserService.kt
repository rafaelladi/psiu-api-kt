package com.dietrich.psiuapikt.service.user

import com.dietrich.psiuapikt.controller.user.req.UserUpdateRequest
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.user.User
import com.dietrich.psiuapikt.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository: UserRepository
) {
    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun find(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw NotFoundException("User", "id", id)
    }

    fun update(id: Long, request: UserUpdateRequest): User {
        val user = find(id)
        request.name?.let {
            user.name = it
        }

        return userRepository.save(user)
    }

    fun delete(id: Long) {
        val user = find(id)
        user.active = false
        userRepository.save(user)
    }
}