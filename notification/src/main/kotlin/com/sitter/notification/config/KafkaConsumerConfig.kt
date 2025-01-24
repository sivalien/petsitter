package com.sitter.notification.config

import com.sitter.notification.message.CustomerCreatedMessage
import com.sitter.notification.message.ReviewMessage
import com.sitter.notification.message.SitterCreatedMessage
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
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfig(
    @Value("\${kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    fun consumerConfig() : Map<String, Any> =
        mapOf<String, Any>(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers)

    @Bean
    fun sitterCreatedFactory() : ConsumerFactory<String, SitterCreatedMessage> {
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            JsonDeserializer(SitterCreatedMessage::class.java, false)
        )
    }

    @Bean
    fun customerCreatedFactory() : ConsumerFactory<String, CustomerCreatedMessage> {
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            JsonDeserializer(CustomerCreatedMessage::class.java, false)
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

    @Bean
    fun reviewFactory() : ConsumerFactory<String, ReviewMessage> = DefaultKafkaConsumerFactory(
        consumerConfig(),
        StringDeserializer(),
        JsonDeserializer(ReviewMessage::class.java, false)
    )

    @Bean
    fun reviewKafkaListenerContainerFactory()
            : KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, ReviewMessage>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, ReviewMessage>()
        factory.consumerFactory = reviewFactory()
        return factory
    }
}