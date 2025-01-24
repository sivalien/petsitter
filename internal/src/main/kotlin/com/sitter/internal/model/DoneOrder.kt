package com.sitter.internal.model

data class DoneOrder(
    val id: Long,
    val sitterId: Long,
    val customer: Customer
)

enum class OrderStatus{ACCEPTED, REJECTED, PENDING}