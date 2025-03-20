package com.PetSitter.view

import com.PetSitter.generated.AdvertCreatedMessage
import com.PetSitter.repository.dto.Advert
import com.PetSitter.repository.dto.Animal
import com.PetSitter.repository.dto.User
import com.fasterxml.jackson.annotation.JsonProperty

data class AdvertResponse (
    @field:JsonProperty("id")
    val id: Long,
    @field:JsonProperty("user")
    val user: UserView,
    @field:JsonProperty("title")
    val title: String,
    @field:JsonProperty("location")
    val location: String,
    @field:JsonProperty("description")
    val description: String,
    @field:JsonProperty("animal_types")
    val animalTypes: List<Animal>,
    @field:JsonProperty("attendance_in")
    val attendanceIn: Boolean,
    @field:JsonProperty("attendance_out")
    val attendanceOut: Boolean,
) {
    fun toAdvertCreatedMessage() : AdvertCreatedMessage.AdvertCreated {
        return AdvertCreatedMessage.AdvertCreated
            .newBuilder()
            .setId(id)
            .setUserId(user.id)
            .setLogin(user.login)
            .setFirstName(user.firstName)
            .setLastName(user.lastName)
            .setTitle(title)
            .setDescription(description)
            .setLocation(location)
            .setAttendanceIn(attendanceIn)
            .setAttendanceOut(attendanceOut)
            .addAllAnimalTypes(animalTypes.map { AdvertCreatedMessage.Animal.valueOf(it.toString()) })
            .build()
    }
}

data class UserView(
    @field:JsonProperty("id")
    val id: Long,
    @field:JsonProperty("login")
    val login: String,
    @field:JsonProperty("first_name")
    val firstName: String,
    @field:JsonProperty("last_name")
    val lastName: String
)

class AdvertHelper {
    companion object {
        fun advertToView(advert: Advert, user: User) : AdvertResponse {
            val animalTypes = mutableListOf<Animal>()
            if (advert.withDog) animalTypes.add(Animal.DOG)
            if (advert.withCat) animalTypes.add(Animal.CAT)
            if (advert.withOther) animalTypes.add(Animal.OTHER)

            return AdvertResponse(
                advert.id,
                UserView(user.id, user.login, user.firstName, user.lastName),
                advert.title,
                advert.location,
                advert.description,
                animalTypes,
                advert.attendanceIn,
                advert.attendanceOut
            )
        }

        fun advertToView(advert: Advert, user: UserView) : AdvertResponse {
            val animalTypes = mutableListOf<Animal>()
            if (advert.withDog) animalTypes.add(Animal.DOG)
            if (advert.withCat) animalTypes.add(Animal.CAT)
            if (advert.withOther) animalTypes.add(Animal.OTHER)

            return AdvertResponse(
                advert.id,
                user,
                advert.title,
                advert.location,
                advert.description,
                animalTypes,
                advert.attendanceIn,
                advert.attendanceOut
            )
        }
    }
}