package com.sitter.internal.config

import com.sitter.internal.view.CustomerView
import com.sitter.internal.view.SitterView
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
    fun consumerConfig() : Map<String, Any> {
//        val valueDeserializer = JsonDeserializer(SitterMessage::class.java)
//        valueDeserializer.addTrustedPackages("com.PetSitter.model.Sitter")
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to "org.apache.kafka.common.serialization.StringDeserializer",
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to valueDeserializer
        )
    }

    @Bean
    fun sitterCreatedFactory() : ConsumerFactory<String, SitterView> {
//        val valueDeserializer = JsonDeserializer(SitterMessage::class.java)
//        valueDeserializer.addTrustedPackages("com.PetSitter.response")
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            JsonDeserializer(SitterView::class.java, false)
        )
    }

    @Bean
    fun customerCreatedFactory() : ConsumerFactory<String, CustomerView> {
        return DefaultKafkaConsumerFactory(
            consumerConfig(),
            StringDeserializer(),
            JsonDeserializer(CustomerView::class.java, false)
        )
    }

    @Bean
    fun sitterCreatedKafkaListenerContainerFactory()
    : KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, SitterView>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SitterView>()
        factory.consumerFactory = sitterCreatedFactory()
        return factory
    }

    @Bean
    fun customerCreatedKafkaListenerContainerFactory()
            : KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, CustomerView>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, CustomerView>()
        factory.consumerFactory = customerCreatedFactory()
        return factory
    }

}