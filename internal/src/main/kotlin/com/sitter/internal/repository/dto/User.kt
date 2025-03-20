package com.sitter.internal.repository.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class User (
    @JsonProperty("id") val id: Long,
    @JsonProperty("login") val login: String,
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
)
