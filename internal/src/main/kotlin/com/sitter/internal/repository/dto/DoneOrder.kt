package com.sitter.internal.repository.dto

data class DoneOrder(
    val id: Long,
    val sitterId: Long,
    val customer: Customer
)

enum class OrderStatus{ACCEPTED, REJECTED, PENDING}