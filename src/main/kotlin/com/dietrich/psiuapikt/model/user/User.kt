package com.dietrich.psiuapikt.model.user

import com.dietrich.psiuapikt.model.Model
import javax.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
class User(
    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role,

    @Column(nullable = false)
    var active: Boolean = true,

    id: Long = 0L
) : Model(id)