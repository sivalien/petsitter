package com.PetSitter.service.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OrderConfirmation(
    @field:JsonProperty("order")
    val order: OrderAction,
    @field:JsonProperty("contacts")
    val contacts: String
)