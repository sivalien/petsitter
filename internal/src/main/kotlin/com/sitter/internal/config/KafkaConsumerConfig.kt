package com.sitter.internal.config

import com.petsitter.generated.AdvertCreatedMessage.CustomerCreatedMessage
import com.petsitter.generated.AdvertCreatedMessage.SitterCreatedMessage
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

@Configuration
class KafkaConsumerConfig(
    @Value("\${kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    fun consumerConfig() : Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        )
    }

    @Bean
    fun sitterCreatedFactory() : ConsumerFactory<String, SitterCreatedMessage> {
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            SitterCreatedMessageDeserializer()
        )
    }

    @Bean
    fun customerCreatedFactory() : ConsumerFactory<String, CustomerCreatedMessage> {
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            CustomerCreatedMessageDeserializer()
        )
    }

    @Bean
    fun sitterCreatedKafkaListenerContainerFactory()
    : KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SitterCreatedMessage>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SitterCreatedMessage>()
        factory.consumerFactory = sitterCreatedFactory()
        return factory
    }

    @Bean
    fun customerCreatedKafkaListenerContainerFactory()
            : KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CustomerCreatedMessage>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, CustomerCreatedMessage>()
        factory.consumerFactory = customerCreatedFactory()
        return factory
    }

}