package com.PetSitter.repository.dto

import com.PetSitter.controller.dto.response.SitterResponse
import com.PetSitter.view.UserView

data class Sitter(
    val advert: Advert,
    val isVet: Boolean
) {
    fun toSitterView(user: User) : SitterResponse {
        return SitterResponse(
            advert.toAdvertView(user),
            isVet
        )
    }

    fun toSitterView(user: UserView) : SitterResponse {
        return SitterResponse(
            advert.toAdvertView(user),
            isVet
        )
    }
}
