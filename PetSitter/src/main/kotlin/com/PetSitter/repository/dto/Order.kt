package com.PetSitter.repository.dto

import java.time.LocalDateTime

data class Order(
    val id: Long,
    val sitterId: Long,
    val customerId: Long,
    val status: OrderStatus,
    val created: LocalDateTime,
    val updated: LocalDateTime,
    val updatedBy: Long
)

enum class OrderStatus {PENDING, ACCEPTED, REJECTED, DONE}