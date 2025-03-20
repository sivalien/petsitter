package com.sitter.internal.config

import com.petsitter.generated.AdvertCreatedMessage.CustomerCreatedMessage
import org.apache.kafka.common.serialization.Deserializer

class CustomerCreatedMessageDeserializer : Deserializer<CustomerCreatedMessage> {
    override fun deserialize(topic: String, data: ByteArray): CustomerCreatedMessage {
        return CustomerCreatedMessage.parseFrom(data)
    }
}