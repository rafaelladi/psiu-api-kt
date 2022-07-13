package com.dietrich.psiuapikt.security

import com.dietrich.psiuapikt.controller.appointment.res.AppointmentValidTokenResponse
import com.dietrich.psiuapikt.controller.auth.res.AuthResponse
import com.dietrich.psiuapikt.exception.APIException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider {
    @Value("\${app.jwt.secret}")
    var secret: String = ""

    @Value("\${app.jwt.access.expiration}")
    var accessExpiration: Int = 0

    @Value("\${app.jwt.refresh.expiration}")
    var refreshExpiration: Int = 0

    fun generateToken(tokenUser: TokenUser): AuthResponse {
        return AuthResponse(
            generateAccessToken(tokenUser),
            generateRefreshToken(tokenUser)
        )
    }

    private fun generateAccessToken(tokenUser: TokenUser): String {
        val currentDate = Date()
        val accessExpirationDate = Date(currentDate.time + accessExpiration)

        return Jwts.builder()
            .setSubject(tokenUser.email)
            .setIssuedAt(currentDate)
            .setExpiration(accessExpirationDate)
            .setClaims(mapOf(
                "userId" to tokenUser.id,
                "orgId" to tokenUser.orgId,
                "projectId" to tokenUser.projectId,
                "role" to tokenUser.role,
                "sub" to tokenUser.email
            ))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    private fun generateRefreshToken(tokenUser: TokenUser): String {
        val currentDate = Date()
        val refreshExpirationDate = Date(currentDate.time + refreshExpiration)

        return Jwts.builder()
            .setSubject(tokenUser.email)
            .setIssuedAt(currentDate)
            .setExpiration(refreshExpirationDate)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }

    private fun getTokenBody(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .body
    }

    fun getUsernameFromToken(token: String): String {
        val claims = getTokenBody(token)
        return claims.subject
    }

    fun getUserDataFromToken(token: String): TokenUser {
        val claims = getTokenBody(token)
        val email = claims.subject
        val id = (claims["userId"] as Int).toLong()
        val orgId = (claims["orgId"] as? Int)?.toLong()
        val projectId = (claims["projectId"] as? Int)?.toLong()
        val role = claims["role"] as String

        return TokenUser(
            email,
            orgId,
            projectId,
            role,
            id
        )
    }

    fun getAppointmentDataFromToken(token: String): AppointmentValidTokenResponse {
        val claims = getTokenBody(token)
        val userId = (claims["userId"] as Int).toLong()
        val appointmentId = (claims["appointmentId"] as Int).toLong()

        return AppointmentValidTokenResponse(userId, appointmentId)
    }

    fun validateToken(token: String): Boolean {
        if(token.isEmpty())
            return false

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) { //TODO DECIDE IF THROW EXCEPTION OR RETURN FALSE
            throw APIException("Invalid JWT signature")
        } catch (e: MalformedJwtException) {
            throw APIException("Invalid JWT token")
        } catch (e: ExpiredJwtException) {
            throw APIException("Expired JWT token")
        } catch (e: UnsupportedJwtException) {
            throw APIException("Unsupported JWT token")
        } catch (e: IllegalArgumentException) {
            throw APIException("JWT claims string is empty")
        }
    }

    fun generateAppointmentToken(appointmentId: Long, userId: Long): String {
        val currentDate = Date()
        val expirationDate = Date(currentDate.time + accessExpiration)

        return Jwts.builder()
            .setIssuedAt(Date())
            .setExpiration(expirationDate)
            .setClaims(mapOf(
                "userId" to userId,
                "appointmentId" to appointmentId,
            ))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact()
    }
}