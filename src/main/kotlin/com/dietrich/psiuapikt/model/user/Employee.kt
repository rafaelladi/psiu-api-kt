package com.dietrich.psiuapikt.model.user

import com.dietrich.psiuapikt.model.appointment.Appointment
import com.dietrich.psiuapikt.model.org.Org
import com.dietrich.psiuapikt.model.org.Project
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapsId
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class Employee(
    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @MapsId
    val user: User,

    @ManyToOne
    @JoinColumn(nullable = false)
    val org: Org,

    @ManyToOne
    @JoinColumn(nullable = true)
    var project: Project? = null,

    @OneToMany(mappedBy = "employee")
    val appointments: MutableSet<Appointment> = mutableSetOf(),

    @Column(nullable = false)
    var averageRating: Double = 5.0,

    @Column(nullable = false)
    var online: Boolean = false,

    @Id
    val id: Long = 0L
)