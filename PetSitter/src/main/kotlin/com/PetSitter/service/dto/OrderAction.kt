package com.PetSitter.service.dto

import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.controller.dto.response.SitterResponse
import com.fasterxml.jackson.annotation.JsonProperty

data class OrderAction(
    @field:JsonProperty("customer")
    val customer: CustomerResponse,
    @field:JsonProperty("sitter")
    val sitter: SitterResponse,
    @field:JsonProperty("to_sitter")
    val toSitter: Boolean
)