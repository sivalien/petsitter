package com.PetSitter.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TokenService(
    @Value("\${jwt.key}")
    key: String
) {
    private val secretKey = Keys.hmacShaKeyFor(key.toByteArray())

    fun generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ) : String = Jwts.builder()
        .claims()
        .subject(userDetails.username)
        .issuedAt(Date(System.currentTimeMillis()))
        .expiration(expirationDate)
        .add(additionalClaims)
        .and()
        .signWith(secretKey)
        .compact()

    fun extractLogin(token: String) : String? =
        getAllClaims(token).subject

    private fun getAllClaims(token: String): Claims {
        val parser = Jwts.parser().verifyWith(secretKey).build()

        return parser.parseSignedClaims(token).payload
    }

    fun isExpired(token: String) : Boolean =
        getAllClaims(token).expiration.before(Date(System.currentTimeMillis()))

    fun isValid(token: String, userDetails: UserDetails) : Boolean {
        return !isExpired(token) && extractLogin(token) == userDetails.username
    }
}