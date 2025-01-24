package com.PetSitter.controller.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    @field:JsonProperty("login")
    val login: String,
    @field:JsonProperty("password")
    val password: String,
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String,
    @field:JsonProperty("contacts")
    val contacts: String
)
