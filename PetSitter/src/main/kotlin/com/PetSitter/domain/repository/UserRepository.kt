package com.PetSitter.domain.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    val jdbcTemplate: JdbcTemplate
) {
    fun create(firstName: String, lastName: String, contacts: String) {
        jdbcTemplate.update(
            "insert into Users (first_name, last_name) values (?, ?)",
            firstName,
            lastName,
            contacts
        )
    }
}
