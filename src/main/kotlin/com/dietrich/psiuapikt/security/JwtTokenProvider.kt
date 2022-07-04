package com.dietrich.psiuapikt.security

import com.dietrich.psiuapikt.exception.APIException
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtTokenProvider {
    @Value("\${app.jwt.secret}")
    var secret: String = ""

    @Value("\${app.jwt.expiration}")
    var expiration: Int = 0

    fun generateToken(authentication: Authentication, id: Long, role: String, orgId: Long? = null, projectId: Long? = null): String {
        val username = authentication.name
        val currentDate = Date()
        val expirationDate = Date(currentDate.time + expiration)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .setClaims(mapOf(
                "userId" to id,
                "orgId" to orgId,
                "projectId" to projectId,
                "role" to role,
                "sub" to username
            ))
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

    fun validateToken(token: String): Boolean {
        if(token.isEmpty())
            return false

        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
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
}