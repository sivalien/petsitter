package com.sitter.internal.model

import com.sitter.internal.view.Animal
import com.sitter.internal.view.Attendance
import com.sitter.internal.view.CustomerView
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
    val attendanceTypes: List<Attendance>,
    val animalTypes: List<Animal>
)
