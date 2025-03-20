package com.sitter.internal.repository.dto

import java.time.LocalDate

data class OrderHistory(
    val id: Long,
    val sitterId: Long,
    val customerId: Long,
    val customerTitle: String,
    val location: String,
    val customerDescription: String,
    val beginDate: LocalDate,
    val endDate: LocalDate,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean,
    val withDog: Boolean,
    val withCat: Boolean,
    val withOther: Boolean
)
