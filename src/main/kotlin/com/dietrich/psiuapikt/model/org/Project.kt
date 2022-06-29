package com.dietrich.psiuapikt.model.org

import com.dietrich.psiuapikt.model.Model
import com.dietrich.psiuapikt.model.user.Employee
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Project(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var type: ProjectType,

    @ManyToOne
    @JoinColumn(nullable = false)
    val org: Org,

    @OneToMany(mappedBy = "project")
    val employees: MutableSet<Employee> = mutableSetOf(),

    @Column(nullable = false)
    var active: Boolean = true,

    id: Long = 0L
) : Model(id)