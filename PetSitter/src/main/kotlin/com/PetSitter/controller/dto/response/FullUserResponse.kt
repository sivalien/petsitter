package com.PetSitter.controller.dto.response

import com.PetSitter.repository.dto.User
import com.fasterxml.jackson.annotation.JsonProperty

data class FullUserResponse(
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String,
    @field:JsonProperty("contacts")
    val contacts: String
) {
    constructor(user: User) : this(
        user.firstName,
        user.lastName,
        user.contacts
    )
}
