package com.dietrich.psiuapikt.repository.user

import com.dietrich.psiuapikt.model.user.Admin
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : JpaRepository<Admin, Long> {
    fun findByOrgId(id: Long): List<Admin>
}