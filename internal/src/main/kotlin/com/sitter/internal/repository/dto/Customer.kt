package com.sitter.internal.repository.dto

import com.sitter.internal.controller.dto.Animal
import com.sitter.internal.controller.dto.CustomerView
import java.time.LocalDate

data class Customer(
    val advert: Advert,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val available: Boolean
) {
    fun toCustomerView() : CustomerView = CustomerView(
        advert.toAdvertView(),
        beginDate,
        endDate
    )
}

data class CustomerFilter(
    val location: String?,
    val dateBegin: LocalDate?,
    val dateEnd: LocalDate?,
    val animalTypes: List<Animal>,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean,
)
