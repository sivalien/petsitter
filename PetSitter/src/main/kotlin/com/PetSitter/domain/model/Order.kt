package com.PetSitter.domain.model

data class Order(
    val id: Int,
    val petSitterId: Int,
    val consumerId: Int,
    val status: OrderStatus
)

enum class OrderStatus {CREATED, ACCEPTED, REJECTED, DONE}