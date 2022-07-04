package com.dietrich.psiuapikt.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val id: Long,
    val orgId: Long?,
    val projectId: Long?,
    private val email: String,
    private val pwd: String,
    private val active: Boolean,
    private val roles: MutableList<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): MutableList<GrantedAuthority> = roles

    override fun getPassword(): String = pwd

    override fun getUsername(): String = email

    override fun isAccountNonExpired(): Boolean = active

    override fun isAccountNonLocked(): Boolean = active

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = active
}