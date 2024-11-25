package com.PetSitter.domain.repository

import com.PetSitter.domain.model.Attendance
import com.PetSitter.domain.model.ConsumerAnnouncement
import com.PetSitter.domain.model.ConsumerAnnouncementDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConsumerAnnouncementRepository(
    private val jdbcTemplate: JdbcTemplate,
    private val namedJdbcTemplate: NamedParameterJdbcTemplate
) {
    fun create(consumerAnnouncementDto: ConsumerAnnouncementDto) {
        jdbcTemplate.update(
            "insert into ConsumerAnnouncement " +
            "(begin_date, end_date, consumer_id, location, description, attendance_in, with_dog, with_cat, with_other) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
            consumerAnnouncementDto.beginDate,
            consumerAnnouncementDto.endDate,
            consumerAnnouncementDto.consumerId,
            consumerAnnouncementDto.location,
            consumerAnnouncementDto.description,
            consumerAnnouncementDto.attendance,
            consumerAnnouncementDto.withDog,
            consumerAnnouncementDto.withCat,
            consumerAnnouncementDto.withOther
        )
    }

    fun findByFilter(
        location: String?,
        dateBegin: LocalDate?,
        dateEnd: LocalDate?,
        attendance: Attendance?,
        withDog: Boolean?,
        withCat: Boolean?,
        withOther: Boolean?,
    ) : List<ConsumerAnnouncement> {
        val namedParameters = mapOf(
            "location" to location,
            "attendance" to attendance,
            "with_dog" to withDog,
            "with_cat" to withCat,
            "with_other" to withOther,
            "date_begin" to dateBegin,
            "date_end" to dateEnd
        )
        return namedJdbcTemplate.query(
            "select * from PetsitterAnnouncement where " +
            if (location == null) "" else "AND location = LOWER(:location) " +
            if (attendance == null) "" else "AND attendance = :attendance " +
            if (withDog == null) "" else "AND with_dog = :with_dog " +
            if (withCat == null) "" else "AND with_cat = :with_cat " +
            if (withOther == null) "" else "AND with_other = :with_other " +
            if (dateBegin != null) "AND begin_date >= :date_begin " else "" +
            if (dateEnd != null) "AND end_date <= :date_end " else "",
            namedParameters,
            mapper
        )
    }

    private val mapper = RowMapper<ConsumerAnnouncement> { rs, _ ->
        ConsumerAnnouncement(
            rs.getLong("id"),
            rs.getLong("consumer_id"),
            rs.getString("description"),
            rs.getBoolean("with_cat"),
            rs.getBoolean("with_dog"),
            rs.getBoolean("with_other"),
            rs.getDate("begin_date").toLocalDate(),
            rs.getDate("end_date").toLocalDate(),
            enumValueOf(rs.getString("attendance_in"))
        )
    }
}
