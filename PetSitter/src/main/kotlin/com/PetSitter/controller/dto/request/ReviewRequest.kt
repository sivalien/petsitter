package com.PetSitter.controller.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ReviewRequest(
    @JsonProperty("sitter_id")
    val sitterId: Long,
    @JsonProperty("score")
    val score: Int,
    @JsonProperty("comment")
    val comment: String?
)
