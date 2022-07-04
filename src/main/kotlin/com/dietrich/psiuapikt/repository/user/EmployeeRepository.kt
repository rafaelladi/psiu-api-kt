package com.dietrich.psiuapikt.repository.user

import com.dietrich.psiuapikt.model.user.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByOrgId(id: Long): List<Employee>
    fun findByProjectId(id: Long): List<Employee>
    @Query("SELECT e FROM Employee e WHERE e.project.id = ?1 AND e.online = true")
    fun findByProjectIdAndOnline(id: Long): List<Employee>
}