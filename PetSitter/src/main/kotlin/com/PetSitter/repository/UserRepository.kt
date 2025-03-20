package com.PetSitter.repository

import com.PetSitter.controller.dto.request.RegisterRequest
import com.PetSitter.controller.dto.request.UserRequest
import com.PetSitter.repository.dto.Role
import com.PetSitter.repository.dto.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val encoder: PasswordEncoder
) {
    fun create(registerRequest: RegisterRequest) : User? {
        return jdbcTemplate.query(
            """
                insert into Users (login, password, first_name, last_name, contacts) values (?, ?, ?, ?, ?)
                returning id, login, password, first_name, last_name, contacts
            """.trimIndent(),
            mapper,
            registerRequest.login,
            encoder.encode(registerRequest.password),
            registerRequest.firstName,
            registerRequest.lastName,
            registerRequest.contacts
        ).singleOrNull()
    }

    fun findById(id: Long) : User? {
        return jdbcTemplate.query(
            "select * from users where id=?",
            mapper,
            id
        ).singleOrNull()
    }

    fun findByLogin(login: String) : User? {
        return jdbcTemplate.query(
            "select * from users where login=?",
            mapper,
            login
        ).singleOrNull()
    }

    private val mapper = RowMapper<User> { rs, _ -> User(
            rs.getLong("id"),
            rs.getString("login"),
            rs.getString("password"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("contacts"),
            Role.USER
        )
    }
}
