package com.dietrich.psiuapikt.security

import com.dietrich.psiuapikt.model.user.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val id: Long,
    private val email: String,
    private val pwd: String,
    private val active: Boolean,
    private val roles: Set<Role>
) : UserDetails {

    override fun getAuthorities(): MutableList<GrantedAuthority> = roles.map {
        SimpleGrantedAuthority(it.name)
    }.toMutableList()

    override fun getPassword(): String = pwd

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = active

    override fun isAccountNonLocked(): Boolean = active

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = active
}