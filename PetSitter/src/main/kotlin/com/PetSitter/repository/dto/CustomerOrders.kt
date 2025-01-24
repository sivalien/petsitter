package com.PetSitter.repository.dto

import com.PetSitter.controller.dto.response.CustomerOrdersResponse
import com.PetSitter.controller.dto.response.SitterResponse
import com.PetSitter.view.UserView
import java.time.LocalDateTime

data class CustomerOrders(
    val id: Long,
    val customerId: Long,
    val sitter: Sitter,
    val user: UserView,
    val status: OrderStatus,
    val created: LocalDateTime,
    val updated: LocalDateTime
) {
    fun toCustomerOrdersResponse() : CustomerOrdersResponse {
        return CustomerOrdersResponse(
            id,
            customerId,
            SitterResponse(
                sitter.advert.toAdvertView(user),
                sitter.isVet,
            ),
            status,
            updated,
            created
        )
    }
}