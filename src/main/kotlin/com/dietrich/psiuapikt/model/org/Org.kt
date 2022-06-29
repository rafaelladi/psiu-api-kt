package com.dietrich.psiuapikt.model.org

import com.dietrich.psiuapikt.model.Model
import com.dietrich.psiuapikt.model.user.Admin
import com.dietrich.psiuapikt.model.user.Employee
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "active = true")
class Org(
    @Column(nullable = false)
    var name: String,
    var description: String,

    @OneToMany(mappedBy = "org")
    val employees: MutableSet<Employee> = mutableSetOf(),

    @OneToMany(mappedBy = "org", cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val admins: MutableSet<Admin> = mutableSetOf(),

    @OneToMany(mappedBy = "org")
    val projects: MutableSet<Project> = mutableSetOf(),

    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var owner: Admin? = null,

    @Column(nullable = false)
    var active: Boolean = true,

    id: Long = 0L
) : Model(id)