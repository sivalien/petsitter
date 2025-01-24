package com.PetSitter.controller.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserRequest(
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String,
    @field:JsonProperty("contacts")
    val contacts: String
)
