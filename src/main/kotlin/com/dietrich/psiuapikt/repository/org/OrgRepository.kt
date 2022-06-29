package com.dietrich.psiuapikt.repository.org

import com.dietrich.psiuapikt.model.org.Org
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrgRepository : JpaRepository<Org, Long>