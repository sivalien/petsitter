package com.PetSitter.domain.repository

import com.PetSitter.domain.model.PetSitterAnnouncement
import com.PetSitter.domain.model.PetSitterAnnouncementDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class PetSitterAnnouncementRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun create(petSitterAnnouncementDto: PetSitterAnnouncementDto) {
        jdbcTemplate.update(
            "insert into PetsitterAnnouncement " +
            "(petsitter_id, location, description, attendance_in, attendance_out, with_dog, with_cat, with_other, is_vet) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
            petSitterAnnouncementDto.petsitter_id,
            petSitterAnnouncementDto.location,
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
        return jdbcTemplate.query(
            "select * from PetsitterAnnouncement where " +
            

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