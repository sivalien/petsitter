package com.sitter.notification.view

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class CustomerView (
    @field:JsonProperty("advert")
    val advert: AdvertView,
    @field:JsonProperty("begin_date")
    val beginDate: LocalDate,
    @field:JsonProperty("end_date")
    val endDate: LocalDate,
)