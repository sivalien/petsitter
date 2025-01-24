package com.sitter.internal.model

import com.sitter.internal.view.AdvertView
import com.sitter.internal.view.Animal
import com.sitter.internal.view.Attendance

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
            if (attendanceIn)
                (if (attendanceOut) listOf(Attendance.IN, Attendance.OUT) else listOf(Attendance.IN))
            else listOf(Attendance.OUT)
        )
    }
}

