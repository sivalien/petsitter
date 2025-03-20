package com.sitter.internal.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.petsitter.generated.AdvertCreatedMessage.AdvertCreated
import com.sitter.internal.repository.dto.User

data class AdvertView (
    @field:JsonProperty("id")
    val id: Long,
    @field:JsonProperty("user")
    val user: User,
    @field:JsonProperty("title")
    val title: String,
    @field:JsonProperty("location")
    val location: String,
    @field:JsonProperty("description")
    val description: String,
    @field:JsonProperty("animal_types")
    val animalTypes: List<Animal>,
    @field:JsonProperty("attendance_in")
    val attendanceIn: Boolean,
    @field:JsonProperty("attendance_out")
    val attendanceOut: Boolean,
) {
    constructor(advertCreated: AdvertCreated) : this(
       advertCreated.id,
        User(
            advertCreated.userId,
            advertCreated.login,
            advertCreated.firstName,
            advertCreated.lastName
        ),
        advertCreated.title,
        advertCreated.location,
        advertCreated.description,
        advertCreated.animalTypesList.map { Animal.valueOf(it.toString()) },
        advertCreated.attendanceIn,
        advertCreated.attendanceOut
    )
}

enum class Attendance { IN, OUT }
enum class Animal {CAT, DOG, OTHER}

