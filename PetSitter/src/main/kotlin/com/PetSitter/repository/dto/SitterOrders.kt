package com.PetSitter.repository.dto

import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.controller.dto.response.SitterOrdersResponse
import com.PetSitter.view.UserView
import java.time.LocalDateTime

data class SitterOrders(
    val id: Long,
    val sitterId: Long,
    val customer: Customer,
    val user: UserView,
    val status: OrderStatus,
    val created: LocalDateTime,
    val updated: LocalDateTime,
) {
    fun toSitterOrdersResponse() : SitterOrdersResponse {
        return SitterOrdersResponse(
            id,
            sitterId,
            CustomerResponse(
                customer.advert.toAdvertView(user),
                customer.beginDate,
                customer.endDate
            ),
            status,
            updated,
            created
        )
    }
}