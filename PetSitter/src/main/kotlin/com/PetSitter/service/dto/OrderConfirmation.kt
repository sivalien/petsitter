package com.PetSitter.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderConfirmation(
    @field:JsonProperty("order")
    val order: OrderRequest,
    @field:JsonProperty("contacts")
    val contacts: String
)