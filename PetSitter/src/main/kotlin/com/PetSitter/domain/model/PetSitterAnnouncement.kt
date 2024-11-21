package com.PetSitter.domain.model

data class PetSitterAnnouncementDto(
    val petsitter_id: Int,
    val location: String,
    val description: String,
    val withCat: Boolean,
    val withDog: Boolean,
    val withOther: Boolean,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean,
    val isVet: Boolean
)

data class PetSitterAnnouncement(
    val id: Int,
    val petsitter_id: Int,
    val location: String,
    val description: String,
    val withCat: Boolean,
    val withDog: Boolean,
    val withOther: Boolean,
    val attendanceIn: Boolean,
    val attendanceOut: Boolean,
    val isVet: Boolean
)
