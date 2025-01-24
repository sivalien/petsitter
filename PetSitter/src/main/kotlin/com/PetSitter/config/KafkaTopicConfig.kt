package com.PetSitter.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfig {

    @Bean
    fun sitterAnnouncementCreatedTopic(
        @Value("\${kafka.sitter.created.topic}")
        topicName: String
    ) : NewTopic {
        return TopicBuilder
            .name(topicName)
//            .partitions(2)
            .build()
    }

    @Bean
    fun customerCreatedTopic(
        @Value("\${kafka.customer.created.topic}") topicName: String
    ) : NewTopic {
        return TopicBuilder
            .name(topicName)
            .partitions(2)
            .build()
    }
}