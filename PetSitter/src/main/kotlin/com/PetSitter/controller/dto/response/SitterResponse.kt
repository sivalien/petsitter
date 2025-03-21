package com.PetSitter.controller.dto.response


import com.PetSitter.generated.AdvertCreatedMessage
import com.PetSitter.generated.AdvertCreatedMessage.SitterCreatedMessage
import com.PetSitter.repository.dto.Attendance
import com.PetSitter.repository.dto.Sitter
import com.PetSitter.repository.dto.User
import com.PetSitter.view.AdvertResponse
import com.fasterxml.jackson.annotation.JsonProperty

class SitterResponse (
    @field:JsonProperty("advert")
    val advert: AdvertResponse,
    @field:JsonProperty("is_vet")
    val isVet: Boolean
) {
    constructor(user: User, sitter: Sitter) : this(
        sitter.advert.toAdvertView(user),
        sitter.isVet
    )

    fun toSitterCreatedMessage() : SitterCreatedMessage {
        return SitterCreatedMessage
            .newBuilder()
            .setAdvert(advert.toAdvertCreatedMessage())
            .setIsVet(isVet)
            .build()
    }
}