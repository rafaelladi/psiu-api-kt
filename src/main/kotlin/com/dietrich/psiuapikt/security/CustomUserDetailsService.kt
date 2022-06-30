package com.dietrich.psiuapikt.security

import com.dietrich.psiuapikt.repository.user.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found for username or email: $username")
        return CustomUserDetails(
            user.id,
            user.email,
            user.password,
            user.active,
            mutableListOf(SimpleGrantedAuthority(user.role.name))
        )
    }
}