package com.PetSitter.service

import com.PetSitter.controller.BadRequestException
import com.PetSitter.controller.ForbiddenException
import com.PetSitter.controller.NotFoundException
import com.PetSitter.controller.dto.request.CustomerFilterRequest
import com.PetSitter.controller.dto.request.CustomerRequest
import com.PetSitter.repository.CustomerRepository
import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.repository.KafkaMessageRepository
import com.PetSitter.repository.dto.*
import com.PetSitter.service.dto.CustomerDto
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@Service
class CustomerService(
    @Value("\${internal.api.url}")
    private val internalApiUrl: String,
    private val customerRepository: CustomerRepository,
    private val restClient: RestClient,
    private val userService: UserService,
    private val kafkaMessageRepository: KafkaMessageRepository
) {
    @Transactional
    fun create(userId: Long, requestDto: CustomerRequest) : CustomerResponse {
        val user = userService.getById(userId)
        if (getEnabledByUserId(userId) != null)
            throw BadRequestException("This user already has customer announcement")
        val customer = customerRepository.create(
            CustomerDto(
                requestDto.advert.toAdvertDto(userId),
                requestDto.beginDate,
                requestDto.endDate
            )
        )!!
        val customerResponse = CustomerResponse(user, customer)
        kafkaMessageRepository.create(
            KafkaMessageType.CUSTOMER_CREATED,
            customerResponse.toCustomerCreatedMessage().toByteArray(),
            KafkaMessageStatus.NEW
        )
        return customerResponse
    }

    fun getById(id: Long) : Customer {
        return customerRepository.findById(id) ?: throw NotFoundException("Customer with id=$id not found")
    }

    fun getEnabledByUserId(userId: Long) : Customer? {
        return customerRepository.getByUserIdAndAvailable(userId, true)
    }

    @Transactional
    fun getViewByUserId(userId: Long) : List<CustomerResponse> {
        return getEnabledByUserId(userId)?.let { listOf(it.toCustomerView(userService.getById(userId))) } ?: emptyList()
    }

    fun disableCustomer(customerId: Long) {
        customerRepository.setAvailable(customerId, false)
    }

    fun enableCustomer(customerId: Long) {
        customerRepository.setAvailable(customerId, true)
    }

    @Transactional
    fun deleteCustomer(userId: Long, customerId: Long) {
        val customer = getById(customerId)
        if (customer.advert.userId != userId)
            throw ForbiddenException("Have not rights to delete this customer announcement")
        customerRepository.deleteCustomer(customerId)
    }

    fun getByFilter(
        filter: CustomerFilterRequest
    ) : List<CustomerResponse> {
        println(filter)
        val uri = UriComponentsBuilder.fromHttpUrl("$internalApiUrl/customer")
            .queryParamIfPresent("location", Optional.ofNullable(filter.location))
            .queryParam("dateBegin", filter.beginDate)
            .queryParam("dateEnd", filter.endDate)
            .queryParam("attendanceIn", filter.attendanceIn)
            .queryParam("attendanceOut", filter.attendanceOut)
            .queryParam("animalTypes", filter.animalTypes ?: emptySet<Animal>())
            .build()
            .toUriString()
        println(uri)

        return restClient.get()
            .uri(uri)
            .retrieve()
            .body(Array<CustomerResponse>::class.java)
            ?.toList() ?: emptyList()
    }
}