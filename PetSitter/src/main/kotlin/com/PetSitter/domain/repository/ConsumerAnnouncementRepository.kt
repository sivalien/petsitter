package com.PetSitter.domain.repository

import com.PetSitter.domain.model.ConsumerAnnouncementDto
import org.jooq.DSLContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ConsumerAnnouncementRepository(
    private val jdbcTemplate: JdbcTemplate
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
            consumerAnnouncementDto.attendanceIn,
            consumerAnnouncementDto.withDog,
            consumerAnnouncementDto.withCat,
            consumerAnnouncementDto.withOther
        )
    }
}
