package com.sitter.notification.service

import com.sitter.notification.message.CustomerCreatedMessage
import com.sitter.notification.message.ReviewMessage
import com.sitter.notification.message.SitterCreatedMessage
import com.sitter.notification.service.MailNotificationService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumersService(
    private val mailNotificationService: MailNotificationService,
    private val orderService: OrderService
) {
    @KafkaListener(
        topics = ["\${kafka.sitter.created.topic}"],
        groupId = "sitter-created",
        containerFactory = "sitterCreatedKafkaListenerContainerFactory")
    fun consume(message: SitterCreatedMessage) {
        println(message)
        mailNotificationService.notifySitterCreated(message)
    }

    @KafkaListener(
        topics = ["\${kafka.customer.created.topic}"],
        groupId = "customer-created",
        containerFactory = "customerCreatedKafkaListenerContainerFactory")
    fun consume(message: CustomerCreatedMessage) {
        println(message)
        mailNotificationService.notifyCustomerCreated(message)
    }

    @KafkaListener(
        topics = ["\${kafka.review.topic}"],
        groupId = "review-request",
        containerFactory = "reviewKafkaListenerContainerFactory")
    fun consume(message: ReviewMessage) {
        println(message)
        orderService.requestOrderReview(message)
    }
}
