package com.PetSitter.repository.dto

import com.PetSitter.view.UserView
import java.time.LocalDateTime

data class FullOrder(
    val id: Long,
    val customer: Customer,
    val customerUser: UserView,
    val sitter: Sitter,
    val sitterUser: UserView,
    val status: OrderStatus,
    val created: LocalDateTime,
    val updated: LocalDateTime
)