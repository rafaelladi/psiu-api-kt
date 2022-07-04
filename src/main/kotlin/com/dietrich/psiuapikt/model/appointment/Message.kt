package com.dietrich.psiuapikt.model.appointment

import com.dietrich.psiuapikt.model.Model
import com.dietrich.psiuapikt.model.user.User
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Message(
    @Column(nullable = false)
    val content: String,

    @Column(nullable = false)
    val sentAt: LocalDateTime,

    @ManyToOne
    @JoinColumn(nullable = false)
    val sentBy: User,

    id: Long = 0L
) : Model(id)