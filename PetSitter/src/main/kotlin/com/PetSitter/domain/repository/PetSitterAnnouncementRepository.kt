package com.PetSitter.domain.repository

import com.PetSitter.domain.model.PetSitterAnnouncement
import com.PetSitter.domain.model.PetSitterAnnouncementDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class PetSitterAnnouncementRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val namedJdbcTemplate: NamedParameterJdbcTemplate
) {
    fun create(petSitterAnnouncementDto: PetSitterAnnouncementDto) {
        jdbcTemplate.update(
            "insert into PetsitterAnnouncement " +
            "(petsitter_id, location, description, attendance_in, attendance_out, with_dog, with_cat, with_other, is_vet) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
            petSitterAnnouncementDto.petsitter_id,
            petSitterAnnouncementDto.location.lowercase(),
            petSitterAnnouncementDto.description,
            petSitterAnnouncementDto.attendanceIn,
            petSitterAnnouncementDto.attendanceOut,
            petSitterAnnouncementDto.withDog,
            petSitterAnnouncementDto.withCat,
            petSitterAnnouncementDto.withOther,
            petSitterAnnouncementDto.isVet
        )
    }

    fun findByFilter(
        location: String?,
        isVet: Boolean?,
        attendanceIn: Boolean?,
        attendanceOut: Boolean?,
        withDog: Boolean?,
        withCat: Boolean?,
        withOther: Boolean?
    ) : List<PetSitterAnnouncement> {
        val namedParameters = mapOf(
            "location" to location,
            "is_vet" to isVet,
            "attendance_in" to attendanceIn,
            "attendance_out" to attendanceOut,
            "with_dog" to withDog,
            "with_cat" to withCat,
            "with_other" to withOther
        )
        return namedJdbcTemplate.query(
            "select * from PetsitterAnnouncement where " +
            if (location == null) "" else "AND location = LOWER(:location) " +
            if (isVet == null) "" else "AND is_vet = :is_vet " +
            if (attendanceIn == null) "" else "AND attendance_in = :attendance_in " +
            if (attendanceOut == null) "" else "AND attendance_out = :attendance_out " +
            if (withDog == null) "" else "AND with_dog = :with_dog " +
            if (withCat == null) "" else "AND with_cat = :with_cat " +
            if (withOther == null) "" else "AND with_other = :with_other ",
            namedParameters,
            mapper
        )
    }

    private val mapper: RowMapper<PetSitterAnnouncement> = RowMapper { rs, _ ->
        PetSitterAnnouncement(
            rs.getInt("id"),
            rs.getInt("petsitter_id"),
            rs.getString("location"),
            rs.getString("description"),
            rs.getBoolean("with_cat"),
            rs.getBoolean("with_dog"),
            rs.getBoolean("with_other"),
            rs.getBoolean("attendance_in"),
            rs.getBoolean("attendance_out"),
            rs.getBoolean("is_vet")
        )
    }
}