package com.PetSitter.repository.dto

data class KafkaMessage (
    val id: Long,
    val messageType: KafkaMessageType,
    val message: ByteArray,
    val status: KafkaMessageStatus
)

enum class KafkaMessageStatus {NEW, SENT}

enum class KafkaMessageType {SITTER_CREATED, CUSTOMER_CREATED}
