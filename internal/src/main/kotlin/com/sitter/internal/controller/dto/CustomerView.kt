package com.sitter.internal.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.protobuf.Timestamp
import com.petsitter.generated.AdvertCreatedMessage.CustomerCreatedMessage
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

data class CustomerView(
    @field:JsonProperty("advert")
    val advert: AdvertView,
    @field:JsonProperty("begin_date")
    val beginDate: LocalDate,
    @field:JsonProperty("end_date")
    val endDate: LocalDate,
) {
    constructor(customerCreatedMessage: CustomerCreatedMessage) : this(
        AdvertView(customerCreatedMessage.advert),
        convertTimestampToLocalDate(customerCreatedMessage.beginDate),
        convertTimestampToLocalDate(customerCreatedMessage.endDate)
    )
}

fun convertTimestampToLocalDate(timestamp: Timestamp): LocalDate {
    val instant = Instant.ofEpochSecond(timestamp.seconds, timestamp.nanos.toLong())
    return instant.atZone(ZoneOffset.UTC).toLocalDate()
}
