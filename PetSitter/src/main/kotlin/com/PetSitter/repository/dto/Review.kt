package com.PetSitter.repository.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Review(
    @JsonProperty("customer_id")
    val customerId: Long,
    @JsonProperty("sitter_id")
    val sitterId: Long,
    @JsonProperty("score")
    val score: Int,
    @JsonProperty("comment")
    val comment: String?
)