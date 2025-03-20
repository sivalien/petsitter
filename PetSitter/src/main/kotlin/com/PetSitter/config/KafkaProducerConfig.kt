package com.PetSitter.config

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
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.ByteArraySerializer"
        )
    }

    @Bean
    fun sitterCreatedFactory() : ProducerFactory<String, ByteArray> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun customerCreatedFactory() : ProducerFactory<String, ByteArray> =
        DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun sitterCreatedKafkaTemplate() : KafkaTemplate<String, ByteArray> =
        KafkaTemplate(sitterCreatedFactory())

    @Bean
    fun customerCreatedKafkaTemplate() : KafkaTemplate<String, ByteArray> =
        KafkaTemplate(customerCreatedFactory())
}