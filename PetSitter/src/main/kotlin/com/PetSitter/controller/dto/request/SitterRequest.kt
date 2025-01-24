package com.PetSitter.controller.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class SitterRequest(
    @field:JsonProperty("advert") val advert: AdvertRequest,
    @field:JsonProperty("is_vet") val isVet: Boolean,
)