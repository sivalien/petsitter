package com.sitter.internal.service

import com.sitter.internal.message.*
import com.sitter.internal.model.CustomerFilter
import com.sitter.internal.repository.CustomerRepository
import com.sitter.internal.view.CustomerView
import com.sitter.internal.view.SitterView
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CustomerService(
    private val customerRepository: CustomerRepository,
    private val sitterCreatedNotificationKafkaTemplate: KafkaTemplate<String, SitterCreatedNotification>,
    @Value("\${kafka.sitter.created.notification.topic}")
    private val sitterCreatedNotificationTopicName: String,
) {
    fun getByFilter(filter: CustomerFilter): List<CustomerView> {
        println("Get customers by filter")
        println(filter)
        return customerRepository.getByFilter(filter).map { it.toCustomerView() }
    }

    fun handleSitterCreated(sitter: SitterView) {
        println(sitter)
        val customers = getByFilter(messageToCustomerFilter(sitter))
            .filter { it.advert.user.id != sitter.advert.user.id }
            .map {
                UserInfo(it.advert.user.id, it.advert.user.login, it.advert.user.firstName, it.advert.user.lastName)
            }
        println(customers)
        if (customers.isNotEmpty())
            sitterCreatedNotificationKafkaTemplate.send(
                sitterCreatedNotificationTopicName,
                SitterCreatedNotification(sitter, customers)
            )
    }

    private fun messageToCustomerFilter(sitterView: SitterView) : CustomerFilter {
        return CustomerFilter(
            sitterView.advert.location,
            LocalDate.now(),
            null,
            sitterView.advert.attendance,
            sitterView.advert.animalTypes
        )
    }
}