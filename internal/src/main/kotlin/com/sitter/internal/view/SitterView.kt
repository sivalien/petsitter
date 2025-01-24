package com.sitter.internal.view

import com.fasterxml.jackson.annotation.JsonProperty

data class SitterView(
    @field:JsonProperty("advert")
    val advert: AdvertView,
    @field:JsonProperty("is_vet")
    val isVet: Boolean,
)
