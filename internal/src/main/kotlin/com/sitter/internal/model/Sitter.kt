package com.sitter.internal.model

import com.sitter.internal.view.Animal
import com.sitter.internal.view.Attendance
import com.sitter.internal.view.SitterView


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
    val attendance: List<Attendance>,
    val animalTypes: List<Animal>,
    val isVet: Boolean?
)
