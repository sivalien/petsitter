package com.PetSitter.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PetSitterAnnouncementRequestDto(
    @field:JsonProperty("description") val description: String,
    @field:JsonProperty("location") val location: String,
    @field:JsonProperty("is_vet") val is_vet: Boolean,
    @field:JsonProperty("attendance_types") val attendanceTypes: Set<Attendance>,
    @field:JsonProperty("animal_types") val animalTypes: Set<Animal>,
)
