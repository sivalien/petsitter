package com.PetSitter.service

import com.PetSitter.controller.NotFoundException
import com.PetSitter.repository.UserRepository
import com.PetSitter.controller.dto.request.RegisterRequest
import com.PetSitter.repository.dto.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) : UserDetailsService {
    fun create(registerRequest: RegisterRequest) : User {
        return userRepository.create(registerRequest)!!
    }

    fun getById(id: Long): User = userRepository.findById(id)
        ?: throw NotFoundException("User not found")

    fun getByLogin(login: String) : User = userRepository.findByLogin(login)
        ?: throw NotFoundException("User with login $login not found")

    override fun loadUserByUsername(username: String): UserDetails {
        return getByLogin(username)
    }
}