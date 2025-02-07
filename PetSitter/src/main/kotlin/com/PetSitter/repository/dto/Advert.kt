package com.PetSitter.repository.dto

import com.PetSitter.view.AdvertResponse
import com.PetSitter.view.UserView

data class Advert(
    val id: Long,
    val userId: Long,
    val title: String,
    val location: String,
    val description: String,
    val withDog: Boolean,
    val withCat: Boolean,
    val withOther: Boolean,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean
) {
    fun toAdvertView(user: User) : AdvertResponse {
        val animalTypes = mutableListOf<Animal>()
        if (withDog) animalTypes.add(Animal.DOG)
        if (withCat) animalTypes.add(Animal.CAT)
        if (withOther) animalTypes.add(Animal.OTHER)

        return AdvertResponse(
            id,
            UserView(user.id, user.login, user.firstName, user.lastName),
            title,
            location,
            description,
            animalTypes,
            if (attendanceIn)
                (if (attendanceOut) listOf(Attendance.IN, Attendance.OUT) else listOf(Attendance.IN))
            else listOf(Attendance.OUT)
        )
    }

    fun toAdvertView(user: UserView) : AdvertResponse {
        val animalTypes = mutableListOf<Animal>()
        if (withDog) animalTypes.add(Animal.DOG)
        if (withCat) animalTypes.add(Animal.CAT)
        if (withOther) animalTypes.add(Animal.OTHER)

        return AdvertResponse(
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

enum class Animal {CAT, DOG, OTHER}
enum class Attendance {
    IN, OUT
}
