package com.PetSitter.service.dto

data class AdvertDto(
    val userId: Long,
    val title: String,
    val location: String,
    val description: String,
    val withDog: Boolean,
    val withCat: Boolean,
    val withOther: Boolean,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean
)