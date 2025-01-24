package com.PetSitter.controller.dto.response

import com.PetSitter.repository.dto.Customer
import com.PetSitter.repository.dto.User
import com.PetSitter.view.AdvertResponse
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class CustomerResponse (
    @field:JsonProperty("advert")
    val advert: AdvertResponse,
    @field:JsonProperty("begin_date")
    val beginDate: LocalDate,
    @field:JsonProperty("end_date")
    val endDate: LocalDate
) {
    constructor(user: User, customer: Customer) : this(
        customer.advert.toAdvertView(user),
        customer.beginDate,
        customer.endDate
    )
}
