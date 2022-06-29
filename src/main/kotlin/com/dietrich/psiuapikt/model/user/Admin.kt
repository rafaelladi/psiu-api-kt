package com.dietrich.psiuapikt.model.user

import com.dietrich.psiuapikt.model.org.Org
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Admin(
    @OneToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    val user: User,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    val org: Org,

    @Id
    val id: Long = 0L,

    @Column(nullable = false)
    var owner: Boolean = true,
)