package com.PetSitter.controller.dto

import com.PetSitter.domain.model.Attendance
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class ConsumerAnnouncementRequestDto(
    @field:JsonProperty("begin_date")
    val beginDate: LocalDate,
    @field:JsonProperty("end_date")
    val endDate: LocalDate,
    @field:JsonProperty("description")
    val description: String,
    @field:JsonProperty("location")
    val location: String,
    @field:JsonProperty("attendance")
    val attendance: Attendance,
    @field:JsonProperty("animals")
    val animals: Set<Animal>
)

enum class Animal {CAT, DOG, OTHER}