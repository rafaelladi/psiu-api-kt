package com.dietrich.psiuapikt.repository.org

import com.dietrich.psiuapikt.model.org.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProjectRepository : JpaRepository<Project, Long> {
    fun findByOrgId(id: Long): List<Project>
}