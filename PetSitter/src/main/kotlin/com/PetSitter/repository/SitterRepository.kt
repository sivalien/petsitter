package com.PetSitter.repository

import com.PetSitter.repository.dto.Advert
import com.PetSitter.repository.dto.Sitter
import com.PetSitter.repository.dto.SitterDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class SitterRepository(
    private val namedJdbcTemplate: NamedParameterJdbcTemplate,
    private val jdbcTemplate: JdbcTemplate
) {
    fun create(sitter: SitterDto) : Sitter? {
        val sql = """
            INSERT INTO sitter
            (user_id, location, title, description, attendance_in, attendance_out, with_dog, with_cat, with_other, is_vet) 
            VALUES (:sitter_id, :location, :title, :description, :attendance_in, :attendance_out, :with_dog, :with_cat, :with_other, :is_vet)
            returning id, user_id, location, title, description, attendance_in, attendance_out, with_dog, with_cat, with_other, is_vet
        """.trimIndent()

        val parameters = mapOf(
            "sitter_id" to sitter.advertDto.userId,
            "title" to sitter.advertDto.title,
            "location" to sitter.advertDto.location,
            "description" to sitter.advertDto.description,
            "attendance_in" to sitter.advertDto.attendanceIn,
            "attendance_out" to sitter.advertDto.attendanceOut,
            "with_dog" to sitter.advertDto.withDog,
            "with_cat" to sitter.advertDto.withCat,
            "with_other" to sitter.advertDto.withOther,
            "is_vet" to sitter.isVet
        )

        return namedJdbcTemplate.query(sql, parameters, mapper).singleOrNull()
    }

    fun findById(id: Long) : Sitter? {
        return namedJdbcTemplate.query(
            "select * from sitter where id = :id",
            mapOf("id" to id),
            mapper
        ).singleOrNull()
    }

    fun findByUserId(userId: Long) : Sitter? {
        return namedJdbcTemplate.query(
            "select * from sitter where user_id = :user_id",
            mapOf("user_id" to userId),
            mapper
        ).firstOrNull()
    }

    fun updateSitter(sitterId: Long, sitter: SitterDto) : Sitter? {
        val parameters = mapOf(
            "sitter_id" to sitterId,
            "location" to sitter.advertDto.location,
            "description" to sitter.advertDto.description,
            "attendance_in" to sitter.advertDto.attendanceIn,
            "attendance_out" to sitter.advertDto.attendanceOut,
            "with_dog" to sitter.advertDto.withDog,
            "with_cat" to sitter.advertDto.withCat,
            "with_other" to sitter.advertDto.withOther,
            "is_vet" to sitter.isVet
        )
        return namedJdbcTemplate.query(
            "update sitter set location=:location, description=:description, is_vet=:is_vet, " +
                    "attendance_in=:attendance_in, attendance_out=:attendance_out, with_dog=:with_dog, " +
                    "with_cat=:with_cat, with_other=:with_other where id=:sitter_id " +
                    "returning id, user_id, location, description, attendance_in, attendance_out, with_dog, with_cat, with_other, is_vet",
            parameters,
            mapper
        ).singleOrNull()
    }

    fun delete(id: Long) {
        jdbcTemplate.update("delete from sitter where id=?", id)
    }

    private val mapper: RowMapper<Sitter> = RowMapper { rs, _ ->
        Sitter(
            Advert(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getString("title"),
                rs.getString("location"),
                rs.getString("description"),
                rs.getBoolean("with_dog"),
                rs.getBoolean("with_cat"),
                rs.getBoolean("with_other"),
                rs.getBoolean("attendance_in"),
                rs.getBoolean("attendance_out"),
            ),
            rs.getBoolean("is_vet")
        )
    }
}