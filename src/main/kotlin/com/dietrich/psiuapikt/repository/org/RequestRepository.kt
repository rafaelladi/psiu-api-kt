package com.dietrich.psiuapikt.repository.org

import com.dietrich.psiuapikt.model.org.Request
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository : JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.status = 'NOT_EVALUATED'")
    fun findNotEvaluated(): List<Request>
}