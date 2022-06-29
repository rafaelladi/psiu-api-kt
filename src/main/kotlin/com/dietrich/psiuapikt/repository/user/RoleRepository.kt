package com.dietrich.psiuapikt.repository.user

import com.dietrich.psiuapikt.model.user.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : JpaRepository<Role, Long>