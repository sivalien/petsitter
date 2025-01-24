package com.PetSitter.controller.dto.response

import com.PetSitter.repository.dto.OrderStatus
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class SitterOrdersResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("sitter_id")
    val sitterId: Long,
    @JsonProperty("customer")
    val customer: CustomerResponse,
    @JsonProperty("status")
    val status: OrderStatus,
    @JsonProperty("updated")
    val updated: LocalDateTime,
    @JsonProperty("created")
    val created: LocalDateTime
)