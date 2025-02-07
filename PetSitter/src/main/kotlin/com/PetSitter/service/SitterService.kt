package com.PetSitter.service

import com.PetSitter.controller.BadRequestException
import com.PetSitter.controller.NotFoundException
import com.PetSitter.controller.dto.request.SitterFilterRequest
import com.PetSitter.repository.SitterRepository
import com.PetSitter.controller.dto.request.SitterRequest
import com.PetSitter.controller.dto.response.SitterResponse
import com.PetSitter.repository.dto.*
import com.PetSitter.service.dto.SitterDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class SitterService(
    private val sitterRepository: SitterRepository,
    private val sitterCreatedKafkaTemplate: KafkaTemplate<String, SitterResponse>,
    @Value("\${kafka.sitter.created.topic}")
    private val sitterCreatedTopicName: String,
    @Value("\${internal.api.url}")
    private val internalApiUrl: String,
    private val restClient: RestClient,
    private val userService: UserService
) {
    @Transactional
    fun create(userId: Long, requestDto: SitterRequest) : SitterResponse {
        val user = userService.getById(userId)
        if (getByUserId(userId) != null)
            throw BadRequestException("This user already has sitter announcement")
        val sitterDto = SitterDto(
            requestDto.advert.toAdvertDto(userId),
            requestDto.isVet
        )
        val sitter = SitterResponse(user, sitterRepository.create(sitterDto)!!)
        sitterCreatedKafkaTemplate.send(sitterCreatedTopicName, sitter)
        return sitter
    }

    fun getById(id: Long) : Sitter {
        return sitterRepository.findById(id)
            ?: throw NotFoundException("Sitter with id=$id not found")
    }

    fun getByUserId(userId: Long) : Sitter? = sitterRepository.findByUserId(userId)

    fun getViewByUserId(userId: Long) : List<SitterResponse> {
        return getByUserId(userId)?.let { listOf(SitterResponse(userService.getById(userId), it)) } ?: emptyList()
    }

    fun getByFilter(
        sitterFilter: SitterFilterRequest
    ) : List<SitterResponse> {
        println(sitterFilter)
        val uri = UriComponentsBuilder.fromHttpUrl("$internalApiUrl/sitter")
            .queryParamIfPresent("location", Optional.ofNullable(sitterFilter.location))
            .queryParam("attendance", sitterFilter.attendanceTypes ?: emptySet<Attendance>())
            .queryParam("animalTypes", sitterFilter.animalTypes ?: emptySet<Animal>())
            .queryParam("isVet", sitterFilter.isVet)
            .build()
            .toUriString()
        println(uri)

        return restClient.get()
            .uri(uri)
            .retrieve()
            .body(Array<SitterResponse>::class.java)
            ?.toList() ?: emptyList()
    }

    fun updateSitter(userId: Long, sitterId: Long, sitterRequest: SitterRequest) : SitterResponse {
        val sitter = getById(sitterId)
        if (sitter.advert.userId != userId)
            throw BadRequestException("Cannot change sitter of another user")
        val sitterDto = SitterDto(
            sitterRequest.advert.toAdvertDto(sitterId),
            sitterRequest.isVet
        )
        return SitterResponse(userService.getById(userId), sitterRepository.updateSitter(sitterId, sitterDto)!!)
    }

    fun delete(userId: Long, sitterId: Long) {
        val sitter = getById(sitterId)
        if (sitter.advert.userId != userId)
            throw BadRequestException("Cannot change sitter of another user")
        sitterRepository.delete(sitterId)
    }
}