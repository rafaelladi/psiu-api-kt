package com.dietrich.psiuapikt.model.user

import com.dietrich.psiuapikt.model.Model
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.UniqueConstraint

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
class Role(
    @Column(unique = true, nullable = false)
    val name: String,

    id: Long = 0L
) : Model(id)