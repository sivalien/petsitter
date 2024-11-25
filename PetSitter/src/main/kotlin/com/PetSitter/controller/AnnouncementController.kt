package com.PetSitter.controller

import com.PetSitter.controller.dto.Animal
import com.PetSitter.controller.dto.ConsumerAnnouncementRequestDto
import com.PetSitter.controller.dto.PetSitterAnnouncementRequestDto
import com.PetSitter.domain.model.Attendance
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate

@Controller
@RequestMapping("/announcement", produces = ["application/json"], consumes = ["application/json"])
class AnnouncementController {
    @PostMapping("/consumer")
    fun createConsumerAnnouncement(
        @RequestBody consumerAnnouncement: ConsumerAnnouncementRequestDto
    ) {

    }

    @PostMapping("/petsitter")
    fun createPetSitterAnnouncement(
        @RequestBody petSitterAnnouncement: PetSitterAnnouncementRequestDto
    ) {

    }

    @GetMapping("/consumer")
    fun getConsumerAnnouncementByFilter(
        @RequestParam("location", required = false) location: String?,
        @RequestParam("begin_date", required = false) beginDate: LocalDate?,
        @RequestParam("end_date", required = false) endDate: LocalDate?,
        @RequestParam("attendance_type", required = false) attendanceType: Attendance?,
        @RequestParam("animal_types", required = false) animalTypes: Set<Animal>
    ) {

    }

    @GetMapping("/petsitter", produces = ["application/json"], consumes = ["application/json"])
    fun getPetSitterAnnouncementByFilter(
        @RequestParam("location", required = false) location: String,
        @RequestParam("attendance_type", required = false) attendanceType: String,
        @RequestParam("animal_types", required = false) animalTypes: Set<Animal>,
        @RequestParam("is_vet", required = false) isVet: Boolean
    ) {

    }
}