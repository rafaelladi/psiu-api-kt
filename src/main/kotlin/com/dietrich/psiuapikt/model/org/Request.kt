package com.dietrich.psiuapikt.model.org

import com.dietrich.psiuapikt.model.Model
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
class Request(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val ownerName: String,

    @Column(nullable = false)
    val ownerEmail: String,

    @Column(nullable = false)
    val ownerPassword: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: RequestStatus = RequestStatus.NOT_EVALUATED,

    id: Long = 0L
) : Model(id)