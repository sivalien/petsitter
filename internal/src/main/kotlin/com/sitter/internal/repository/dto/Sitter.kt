package com.sitter.internal.repository.dto

import com.sitter.internal.controller.dto.Animal
import com.sitter.internal.controller.dto.SitterView


data class Sitter(
    val advert: Advert,
    val isVet: Boolean
) {
    fun toSitterView() : SitterView = SitterView(
        advert.toAdvertView(),
        isVet
    )
}

data class SitterFilter(
    val location: String?,
    val animalTypes: List<Animal>,
    val isVet: Boolean?,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean,
)
