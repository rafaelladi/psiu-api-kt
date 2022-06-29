package com.dietrich.psiuapikt.repository.user

import com.dietrich.psiuapikt.model.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
    fun findByEmailAndPassword(email: String, password: String): User?
}