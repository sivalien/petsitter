package com.sitter.internal

import com.sitter.internal.service.CustomerService
import com.sitter.internal.service.SitterService
import com.sitter.internal.view.CustomerView
import com.sitter.internal.view.SitterView
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumersService(
    private val customerService: CustomerService,
    private val sitterService: SitterService
) {
    @KafkaListener(
        topics = ["\${kafka.sitter.created.topic}"],
        groupId = "sitter-created",
        containerFactory = "sitterCreatedKafkaListenerContainerFactory")
    fun consume(message: SitterView) {
        customerService.handleSitterCreated(message)
    }

    @KafkaListener(
        topics = ["\${kafka.customer.created.topic}"],
        groupId = "customer-created",
        containerFactory = "customerCreatedKafkaListenerContainerFactory")
    fun consume(message: CustomerView) {
        sitterService.handleCustomerCreated(message)
    }
}