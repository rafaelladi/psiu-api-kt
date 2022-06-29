package com.dietrich.psiuapikt.repository.appointment

import com.dietrich.psiuapikt.model.appointment.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, Long>