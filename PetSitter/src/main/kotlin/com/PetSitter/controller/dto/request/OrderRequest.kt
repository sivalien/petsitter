package com.PetSitter.controller.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderRequest (
    @field:JsonProperty("sitter_id")
    val sitterId: Long,
    @field:JsonProperty("consumer_id")
    val consumerId: Long
)