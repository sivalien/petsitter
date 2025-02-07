package com.PetSitter.repository.dto

import com.PetSitter.view.AdvertHelper
import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.view.UserView
import java.time.LocalDate

data class Customer(
    val advert: Advert,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val available: Boolean
) {
    val id = advert.id
    val userId = advert.userId

    fun toCustomerView(user: User) : CustomerResponse {
        return CustomerResponse(
            AdvertHelper.advertToView(advert, user),
            beginDate,
            endDate
        )
    }

    fun toCustomerView(user: UserView) : CustomerResponse {
        return CustomerResponse(
            advert.toAdvertView(user),
            beginDate,
            endDate
        )
    }
}

