package com.PetSitter.domain.model

import java.time.LocalDate

data class ConsumerAnnouncementDto(
    val consumerId: Int,
    val location: String,
    val description: String,
    val withCat: Boolean,
    val withDog: Boolean,
    val withOther: Boolean,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val attendanceIn: Boolean,
)

data class ConsumerAnnouncement(
    val id: Int,
    val consumerId: Int,
    val description: String,
    val withCat: Boolean,
    val withDog: Boolean,
    val withOther: Boolean,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val attendanceIn: Boolean,
)
