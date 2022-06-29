package com.dietrich.psiuapikt.config.extension

import com.dietrich.psiuapikt.model.user.Role
import org.springframework.security.core.authority.SimpleGrantedAuthority

fun Set<Role>.toAuthorities(): List<SimpleGrantedAuthority> = this.map {
    SimpleGrantedAuthority(it.name)
}.toMutableList()