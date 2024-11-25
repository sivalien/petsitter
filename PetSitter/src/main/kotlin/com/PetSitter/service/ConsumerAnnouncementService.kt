package com.PetSitter.service

import com.PetSitter.controller.dto.Animal
import com.PetSitter.controller.dto.ConsumerAnnouncementRequestDto
import com.PetSitter.domain.model.Attendance
import com.PetSitter.domain.model.ConsumerAnnouncement
import com.PetSitter.domain.model.ConsumerAnnouncementDto
import com.PetSitter.domain.repository.ConsumerAnnouncementRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ConsumerAnnouncementService(
    private val consumerAnnouncementRepository: ConsumerAnnouncementRepository
) {
    fun create(consumerId: Long, requestDto: ConsumerAnnouncementRequestDto) {
        if (requestDto.beginDate.isAfter(requestDto.endDate))

        consumerAnnouncementRepository.create(ConsumerAnnouncementDto(
            consumerId,
            requestDto.location,
            requestDto.description,
            requestDto.animals.contains(Animal.CAT),
            requestDto.animals.contains(Animal.DOG),
            requestDto.animals.contains(Animal.OTHER),
            requestDto.beginDate,
            requestDto.endDate,
            requestDto.attendance
        ))
    }

    fun getByFilter(
        location: String?,
        beginDate: LocalDate?,
        endDate: LocalDate?,
        attendanceType: Attendance?,
        animalTypes: Set<Animal>
    ) : List<ConsumerAnnouncement> {
        return consumerAnnouncementRepository.findByFilter(
            location,
            beginDate,
            endDate,
            attendanceType,
            animalTypes.contains(Animal.DOG),
            animalTypes.contains(Animal.CAT),
            animalTypes.contains(Animal.OTHER)
        )
    }
}