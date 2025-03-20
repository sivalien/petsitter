package com.PetSitter.service

import com.PetSitter.repository.KafkaMessageRepository
import com.PetSitter.repository.dto.KafkaMessage
import com.PetSitter.repository.dto.KafkaMessageStatus
import com.PetSitter.repository.dto.KafkaMessageType
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class KafkaMessageSendingService(
    private val kafkaMessageRepository: KafkaMessageRepository,
    @Value("\${kafka.sitter.created.topic}")
    sitterCreatedTopic: String,
    @Value("\${kafka.customer.created.topic}")
    customerCreatedTopic: String,
    sitterCreatedKafkaTemplate: KafkaTemplate<String, ByteArray>,
    customerCreatedKafkaTemplate: KafkaTemplate<String, ByteArray>,
    @Value("\${kafka.message.interval.in.minutes}")
    private val interval: Long,
) {
    val messageTypeToTopic = mapOf(
        KafkaMessageType.SITTER_CREATED to sitterCreatedTopic,
        KafkaMessageType.CUSTOMER_CREATED to customerCreatedTopic
    )

    val messageTypeToKafkaTemplate = mapOf(
        KafkaMessageType.SITTER_CREATED to sitterCreatedKafkaTemplate,
        KafkaMessageType.CUSTOMER_CREATED to customerCreatedKafkaTemplate
    )

    init {
        val executor = Executors.newScheduledThreadPool(2)
        executor.scheduleAtFixedRate(
            { doJob() },
            1,
            interval,
            TimeUnit.MINUTES
        )
    }

    fun doJob() {
        print("searching for new messages")
        val messages = kafkaMessageRepository.getByStatus(KafkaMessageStatus.NEW)
        println("KAFKA MESSAGE SERVICE got ${messages.size}")
        messages.forEach {
            sendMessage(it)
        }
    }

    fun sendMessage(message: KafkaMessage) {
        val messageType = message.messageType
        messageTypeToKafkaTemplate[messageType]!!.send(
            messageTypeToTopic[messageType]!!,
            message.message
        )
        kafkaMessageRepository.setStatusById(message.id, KafkaMessageStatus.SENT)
    }
}
