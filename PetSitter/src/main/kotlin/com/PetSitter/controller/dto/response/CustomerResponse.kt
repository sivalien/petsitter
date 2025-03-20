package com.PetSitter.controller.dto.response

import com.PetSitter.generated.AdvertCreatedMessage.CustomerCreatedMessage
import com.PetSitter.repository.dto.Customer
import com.PetSitter.repository.dto.User
import com.PetSitter.view.AdvertResponse
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.protobuf.Timestamp
import java.time.LocalDate
import java.time.ZoneOffset

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

    fun toCustomerCreatedMessage() : CustomerCreatedMessage {
        return CustomerCreatedMessage
            .newBuilder()
            .setAdvert(advert.toAdvertCreatedMessage())
            .setBeginDate(convertLocalDateToTimestamp(beginDate))
            .setEndDate(convertLocalDateToTimestamp(endDate))
            .build()
    }
}

fun convertLocalDateToTimestamp(localDate: LocalDate): Timestamp {
    val instant = localDate.atStartOfDay().toInstant(ZoneOffset.UTC)
    return Timestamp.newBuilder()
        .setSeconds(instant.epochSecond)
        .setNanos(instant.nano)
        .build()
}
