package com.PetSitter.controller.dto.request

import com.PetSitter.service.dto.AdvertDto
import com.PetSitter.repository.dto.Animal
import com.fasterxml.jackson.annotation.JsonProperty

data class AdvertRequest(
    @field:JsonProperty("title") val title: String,
    @field:JsonProperty("description") val description: String,
    @field:JsonProperty("location") val location: String,
    @field:JsonProperty("animal_types") val animalTypes: Set<Animal>,
    @field:JsonProperty("attendance_in") val attendanceIn: Boolean,
    @field:JsonProperty("attendance_out") val attendanceOut: Boolean
) {
    fun toAdvertDto(userId: Long) : AdvertDto {
        return AdvertDto(
            userId,
            title,
            location,
            description,
            animalTypes.contains(Animal.DOG),
            animalTypes.contains(Animal.CAT),
            animalTypes.contains(Animal.OTHER),
            attendanceIn,
            attendanceOut
        )
    }
}
