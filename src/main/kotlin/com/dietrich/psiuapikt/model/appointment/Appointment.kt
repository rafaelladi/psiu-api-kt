package com.dietrich.psiuapikt.model.appointment

import com.dietrich.psiuapikt.model.Model
import com.dietrich.psiuapikt.model.org.Project
import com.dietrich.psiuapikt.model.user.Employee
import com.dietrich.psiuapikt.model.user.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Appointment(
    @Column(nullable = false)
    val start: LocalDateTime, //should only be filled if it is a scheduled appointment

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: AppointmentStatus,

    @ManyToOne
    @JoinColumn(nullable = false)
    val project: Project,

    @ManyToOne
    @JoinColumn(nullable = false)
    val employee: Employee,

    @ManyToOne
    @JoinColumn(nullable = false)
    val user: User,

    val note: String? = null,

    val rating: Double? = null,

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    @OrderBy("sentAt")
    val messages: MutableList<Message> = mutableListOf(),

    id: Long = 0L
) : Model(id)