package com.dietrich.psiuapikt.model.user

import com.dietrich.psiuapikt.model.Model
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class User(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var active: Boolean = true,

    @ManyToMany
    @JoinTable(
        joinColumns = [JoinColumn(name = "user_id", nullable = false)],
        inverseJoinColumns = [JoinColumn(name = "role_id", nullable = false)]
    )
    val roles: MutableSet<Role> = mutableSetOf(),

    id: Long = 0L
) : Model(id)