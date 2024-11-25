package com.PetSitter.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequestDto(
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String,
    @field:JsonProperty("contacts")
    val contacts: String
)
