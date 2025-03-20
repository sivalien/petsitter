package com.sitter.internal.repository.dto

import com.sitter.internal.controller.dto.AdvertView
import com.sitter.internal.controller.dto.Animal


data class Advert(
    val id: Long,
    val user: User,
    val title: String,
    val location: String,
    val description: String,
    val withDog: Boolean,
    val withCat: Boolean,
    val withOther: Boolean,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean
) {
    fun toAdvertView() : AdvertView {
        val animalTypes = mutableListOf<Animal>()
        if (withDog) animalTypes.add(Animal.DOG)
        if (withCat) animalTypes.add(Animal.CAT)
        if (withOther) animalTypes.add(Animal.OTHER)

        return AdvertView(
            id,
            user,
            title,
            location,
            description,
            animalTypes,
            attendanceIn,
            attendanceOut
        )
    }
}

