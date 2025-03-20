package com.sitter.internal.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.petsitter.generated.AdvertCreatedMessage.SitterCreatedMessage

data class SitterView(
    @field:JsonProperty("advert")
    val advert: AdvertView,
    @field:JsonProperty("is_vet")
    val isVet: Boolean,
) {
    constructor(sitterCreatedMessage: SitterCreatedMessage) : this(
        AdvertView(sitterCreatedMessage.advert),
        sitterCreatedMessage.isVet
    )
}
