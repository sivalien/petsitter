package com.PetSitter.config

import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.controller.dto.response.SitterResponse
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
    fun sitterCreatedFactory() : ProducerFactory<String, SitterResponse> {
        return DefaultKafkaProducerFactory(producerConfig())
    }

    @Bean
    fun customerCreatedFactory() : ProducerFactory<String, CustomerResponse> =
        DefaultKafkaProducerFactory(producerConfig())

    @Bean
    fun sitterCreatedKafkaTemplate() : KafkaTemplate<String, SitterResponse> =
        KafkaTemplate(sitterCreatedFactory())

    @Bean
    fun customerCreatedKafkaTemplate() : KafkaTemplate<String, CustomerResponse> =
        KafkaTemplate(customerCreatedFactory())
}