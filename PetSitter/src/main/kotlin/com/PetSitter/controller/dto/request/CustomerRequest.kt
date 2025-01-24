package com.PetSitter.controller.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class CustomerRequest(
    @field:JsonProperty("begin_date") val beginDate: LocalDate,
    @field:JsonProperty("end_date") val endDate: LocalDate,
    @field:JsonProperty("advert") val advert: AdvertRequest
)

