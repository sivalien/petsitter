package com.PetSitter.controller.dto.request

import com.PetSitter.repository.dto.Animal
import com.PetSitter.repository.dto.Attendance
import java.time.LocalDate

data class CustomerFilterRequest(
    val location: String?,
    val beginDate: LocalDate?,
    val endDate: LocalDate?,
    val animalTypes: Set<Animal>?,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean
)