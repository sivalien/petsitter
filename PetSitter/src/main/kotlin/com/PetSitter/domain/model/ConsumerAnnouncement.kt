package com.PetSitter.domain.model

import java.time.LocalDate

data class ConsumerAnnouncementDto(
    val consumerId: Long,
    val location: String,
    val description: String,
    val withCat: Boolean,
    val withDog: Boolean,
    val withOther: Boolean,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val attendance: Attendance,
)

data class ConsumerAnnouncement(
    val id: Long,
    val consumerId: Long,
    val description: String,
    val withCat: Boolean,
    val withDog: Boolean,
    val withOther: Boolean,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val attendance: Attendance,
)

enum class Attendance {
    IN, OUT
}