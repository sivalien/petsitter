package com.PetSitter.config

import com.PetSitter.service.TokenService
import com.PetSitter.service.UserService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val userService: UserService,
    private val tokenService: TokenService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(AUTH_HEADER_NAME)

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader.substring(7)
        val login = tokenService.extractLogin(jwtToken)

        if (login != null && SecurityContextHolder.getContext().authentication == null) {
            val foundUser = userService.getByLogin(login)

            if (tokenService.isValid(jwtToken, foundUser)) {
                val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authToken
                request.setAttribute("uid", foundUser.id)
                filterChain.doFilter(request, response)

            }
        }
    }

    companion object {
        const val BEARER_PREFIX = "Bearer"
        const val AUTH_HEADER_NAME = "Authorization"
    }
}