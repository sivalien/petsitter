package com.sitter.notification.view

import com.fasterxml.jackson.annotation.JsonProperty

data class UserView(
    @JsonProperty("id") val id: Long,
    @JsonProperty("login") val login: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
)

data class AdvertView (
    @field:JsonProperty("id")
    val id: Long,
    @field:JsonProperty("user")
    val user: UserView,
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
