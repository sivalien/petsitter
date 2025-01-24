package com.PetSitter.controller.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    @field:JsonProperty("id")
    val id: Long,
    @field:JsonProperty("login")
    val login: String,
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String,
    @field:JsonProperty("contacts")
    val contacts: String
)