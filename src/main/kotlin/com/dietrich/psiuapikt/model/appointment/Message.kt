package com.dietrich.psiuapikt.model.appointment

import com.dietrich.psiuapikt.model.Model
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class Message(
    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val sentAt: LocalDateTime,

    id: Long = 0L
) : Model(id)