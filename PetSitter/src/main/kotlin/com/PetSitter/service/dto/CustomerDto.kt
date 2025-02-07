package com.PetSitter.service.dto

import java.time.LocalDate

data class CustomerDto(
    val advert: AdvertDto,
    val beginDate: LocalDate,
    val endDate: LocalDate
)