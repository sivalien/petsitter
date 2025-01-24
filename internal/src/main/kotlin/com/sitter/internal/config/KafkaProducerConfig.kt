package com.sitter.internal.config

import com.sitter.internal.message.CustomerCreatedNotification
import com.sitter.internal.message.ReviewNotification
import com.sitter.internal.message.SitterCreatedNotification
import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig(
    @Value("\${kafka.bootstrap-servers}")
    private val bootstrapServers: String
) {
    fun producerConfig() : Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringSerializer",
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to "org.springframework.kafka.support.serializer.JsonSerializer"
        )
    }

    @Bean
    fun customerCreatedNotificationFactory() : ProducerFactory<String, CustomerCreatedNotification> =
        DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun customerCreatedNotificationKafkaTemplate() : KafkaTemplate<String, CustomerCreatedNotification> =
        KafkaTemplate(customerCreatedNotificationFactory())

    @Bean
    fun sitterCreatedNotificationFactory() : ProducerFactory<String, SitterCreatedNotification> =
        DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun sitterCreatedNotificationKafkaTemplate() : KafkaTemplate<String, SitterCreatedNotification> =
        KafkaTemplate(sitterCreatedNotificationFactory())

    @Bean
    fun reviewNotificationFactory() : ProducerFactory<String, ReviewNotification> =
        DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun reviewNotificationKafkaTemplate() : KafkaTemplate<String, ReviewNotification> =
        KafkaTemplate(reviewNotificationFactory())
}