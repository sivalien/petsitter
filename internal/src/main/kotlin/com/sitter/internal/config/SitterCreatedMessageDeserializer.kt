package com.sitter.internal.config

import com.petsitter.generated.AdvertCreatedMessage.SitterCreatedMessage
import org.apache.kafka.common.serialization.Deserializer

class SitterCreatedMessageDeserializer : Deserializer<SitterCreatedMessage> {
    override fun deserialize(topic: String, data: ByteArray): SitterCreatedMessage {
        return SitterCreatedMessage.parseFrom(data)
    }
}