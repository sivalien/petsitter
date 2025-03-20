package com.PetSitter.controller.dto.request

import com.PetSitter.repository.dto.Animal
import com.PetSitter.repository.dto.Attendance

data class SitterFilterRequest(
    val location: String?,
    val animalTypes: List<Animal>?,
    val isVet: Boolean?,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean
)