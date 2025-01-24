package com.PetSitter.controller.dto.response

import com.PetSitter.repository.dto.OrderStatus
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class CustomerOrdersResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("customer_id")
    val customerId: Long,
    @JsonProperty("sitter")
    val sitter: SitterResponse,
    @JsonProperty("status")
    val status: OrderStatus,
    @JsonProperty("updated")
    val updated: LocalDateTime,
    @JsonProperty("created")
    val created: LocalDateTime
)