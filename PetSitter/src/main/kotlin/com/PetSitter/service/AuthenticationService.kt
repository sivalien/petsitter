package com.PetSitter.service

import com.PetSitter.controller.dto.response.AuthenticationResponse
import com.PetSitter.controller.dto.request.LoginRequest
import com.PetSitter.controller.dto.request.RegisterRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
    @Value("\${jwt.access.token.expiration}")
    private val accessTokenExpiration: Long,
    private val userService: UserService
) {
    fun register(registerRequest: RegisterRequest) : AuthenticationResponse {
        val user = userService.create(registerRequest)

        val accessToken = tokenService.generate(
            user,
            Date(System.currentTimeMillis() + accessTokenExpiration),
            mapOf("id" to user.id)
        )

        return AuthenticationResponse(accessToken = accessToken)
    }

    fun authenticate(loginRequest: LoginRequest) : AuthenticationResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            loginRequest.login,
            loginRequest.password
        ))

        val user = userService.getByLogin(loginRequest.login)
        val accessToken = tokenService.generate(
            user,
            Date(System.currentTimeMillis() + accessTokenExpiration),
            mapOf("id" to user.id)
        )

        return AuthenticationResponse(accessToken = accessToken)
    }
}