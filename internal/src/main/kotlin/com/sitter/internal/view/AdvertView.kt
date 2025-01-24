package com.sitter.internal.view

import com.fasterxml.jackson.annotation.JsonProperty
import com.sitter.internal.model.Advert
import com.sitter.internal.model.User

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
    @field:JsonProperty("attendance_types")
    val attendance: List<Attendance>,
)

enum class Attendance { IN, OUT }
enum class Animal {CAT, DOG, OTHER}

