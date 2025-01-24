package com.PetSitter.repository.dto

import com.PetSitter.controller.dto.response.UserResponse
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val id: Long,
    val login: String,
    val userPassword: String,
    val firstName: String,
    val lastName: String,
    val contacts: String,
    val role: Role
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return userPassword
    }

    override fun getUsername(): String {
        return login
    }

    fun toUserResponse() : UserResponse = UserResponse(id, login, firstName, lastName, contacts)
}

enum class Role {USER}
