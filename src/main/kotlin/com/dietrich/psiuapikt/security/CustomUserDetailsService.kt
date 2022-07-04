package com.dietrich.psiuapikt.security

import com.dietrich.psiuapikt.repository.user.AdminRepository
import com.dietrich.psiuapikt.repository.user.EmployeeRepository
import com.dietrich.psiuapikt.repository.user.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val userRepository: UserRepository,
    val adminRepository: AdminRepository,
    val employeeRepository: EmployeeRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByEmail(username) ?: throw UsernameNotFoundException("User not found for username or email: $username")
        val admin = adminRepository.findByIdOrNull(user.id)
        val employee = employeeRepository.findByIdOrNull(user.id)
        return CustomUserDetails(
            user.id,
            admin?.org?.id,
            employee?.project?.id,
            user.email,
            user.password,
            user.active,
            mutableListOf(SimpleGrantedAuthority(user.role.name))
        )
    }
}