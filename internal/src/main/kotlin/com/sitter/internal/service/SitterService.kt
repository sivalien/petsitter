package com.sitter.internal.service

import com.sitter.internal.message.*
import com.sitter.internal.model.SitterFilter
import com.sitter.internal.repository.SitterRepository
import com.sitter.internal.view.CustomerView
import com.sitter.internal.view.SitterView
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class SitterService(
    private val sitterRepository: SitterRepository,
    private val customerCreatedNotificationKafkaTemplate: KafkaTemplate<String, CustomerCreatedNotification>,
    @Value("\${kafka.customer.created.notification.topic}")
    private val customerCreatedNotificationTopicName: String
) {
    fun getByFilter(filter: SitterFilter) : List<SitterView> {
        return sitterRepository.getByFilter(filter).map { it.toSitterView() }
    }

    fun handleCustomerCreated(customerView: CustomerView) {
        println(customerView)
        val sitters = getByFilter(advertViewToSitterFilter(customerView))
            .filter { it.advert.user.id != customerView.advert.user.id }
            .map { UserInfo(it.advert.user.id, it.advert.user.login, it.advert.user.firstName, it.advert.user.lastName) }
        println(sitters)
        if (sitters.isNotEmpty())
            customerCreatedNotificationKafkaTemplate.send(
                customerCreatedNotificationTopicName,
                CustomerCreatedNotification(customerView, sitters)
            )
    }

    private fun advertViewToSitterFilter(customerView: CustomerView) : SitterFilter {
        return SitterFilter(
            customerView.advert.location,
            customerView.advert.attendance,
            customerView.advert.animalTypes,
            null
        )
    }
}