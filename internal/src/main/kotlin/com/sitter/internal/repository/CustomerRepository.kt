package com.sitter.internal.repository

import com.sitter.internal.controller.dto.Animal
import com.sitter.internal.repository.dto.Advert
import com.sitter.internal.repository.dto.Customer
import com.sitter.internal.repository.dto.CustomerFilter
import com.sitter.internal.repository.dto.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CustomerRepository(
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate
) {
    fun getByFilter(filter: CustomerFilter) : List<Customer> {
        println(filter.location)
        val params = mapOf(
            "location" to filter.location?.lowercase(),
            "date_begin" to filter.dateBegin,
            "date_end" to filter.dateEnd
        )

        val conditions = emptyList<String>().toMutableList()

        filter.location?.let { conditions.add("LOWER(location) = LOWER(:location)") }
        filter.dateBegin?.let { conditions.add("begin_date >= :date_begin ") }
        filter.dateEnd?.let { conditions.add("end_date <= :date_end") }

        if (filter.attendanceIn && !filter.attendanceOut)
            conditions.add("attendance_in=true")
        else if (!filter.attendanceIn && filter.attendanceOut)
            conditions.add("attendance_out=true")

        val animalConditions = filter.animalTypes.joinToString(" or ") { "${animalMapper[it]}=true" }
        if (animalConditions.isNotEmpty()) conditions.add("($animalConditions)")

        val query = "select customer.id, title, location, description, with_dog, with_cat, with_other, " +
                "attendance_in, attendance_out, user_id, first_name, last_name, " +
                "begin_date, end_date, available, login " +
                " from customer inner join users on customer.user_id = users.id " +
                " where available=true " +
                if (conditions.isNotEmpty()) conditions.joinToString(" and ", " and ") else ""

        return namedParameterJdbcTemplate.query(query, params, mapper)
    }

    private val mapper = RowMapper<Customer> { rs, _ ->
        Customer(
            Advert(
                rs.getLong("id"),
                User(
                    rs.getLong("user_id"),
                    rs.getString("login"),
                    rs.getString("first_name"),
                    rs.getString("last_name")
                ),
                rs.getString("title"),
                rs.getString("location"),
                rs.getString("description"),
                rs.getBoolean("with_dog"),
                rs.getBoolean("with_cat"),
                rs.getBoolean("with_other"),
                rs.getBoolean("attendance_in"),
                rs.getBoolean("attendance_out")
            ),
            rs.getDate("begin_date").toLocalDate(),
            rs.getDate("end_date").toLocalDate(),
            rs.getBoolean("available")
        )
    }

    private val animalMapper = mapOf(Animal.DOG to "with_dog", Animal.CAT to "with_cat", Animal.OTHER to "with_other")
}