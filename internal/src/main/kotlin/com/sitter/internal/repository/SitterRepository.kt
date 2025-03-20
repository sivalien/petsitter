package com.sitter.internal.repository

import com.sitter.internal.controller.dto.Animal
import com.sitter.internal.repository.dto.Advert
import com.sitter.internal.repository.dto.Sitter
import com.sitter.internal.repository.dto.SitterFilter
import com.sitter.internal.repository.dto.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SitterRepository(
    private val namedJdbcTemplate: NamedParameterJdbcTemplate
) {

    fun getByFilter(filter: SitterFilter) : List<Sitter> {
        val params = mapOf(
            "location" to filter.location?.lowercase(),
            "is_vet" to filter.isVet
        )
        val conditions = emptyList<String>().toMutableList()
        filter.location?.let { conditions.add("lower(location) = :location") }
        filter.isVet?.let { conditions.add("is_vet = :is_vet") }

        filter.animalTypes.forEach{ conditions.add("${animalMapper[it]}=true") }

        if (filter.attendanceIn && !filter.attendanceOut)
            conditions.add("attendance_in=true")
        else if (!filter.attendanceIn && filter.attendanceOut)
            conditions.add("attendance_out=true")

        val query = "select sitter.id as sitter_id, title, location, description, attendance_in, attendance_out, " +
                "with_dog, with_cat, with_other, is_vet, user_id, first_name, last_name, is_vet, login " +
                "from sitter inner join users on sitter.user_id = users.id " +
                if (conditions.isNotEmpty()) conditions.joinToString(" and ", " where ") else ""

        return namedJdbcTemplate.query(query, params, mapper)
    }

    private val mapper: RowMapper<Sitter> = RowMapper { rs, _ ->
        Sitter(
            Advert(
                rs.getLong("sitter_id"),
                User(
                    rs.getLong("user_id"),
                    rs.getString("login"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                ),
                rs.getString("title"),
                rs.getString("location"),
                rs.getString("description"),
                rs.getBoolean("with_cat"),
                rs.getBoolean("with_dog"),
                rs.getBoolean("with_other"),
                rs.getBoolean("attendance_in"),
                rs.getBoolean("attendance_out"),
            ),
            rs.getBoolean("is_vet")
        )
    }

    private val animalMapper = mapOf(Animal.DOG to "with_dog", Animal.CAT to "with_cat", Animal.OTHER to "with_other")
}