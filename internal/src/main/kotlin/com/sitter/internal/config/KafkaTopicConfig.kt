package com.sitter.internal.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfig {
    @Bean
    fun notifyCustomerCreatedTopic(
        @Value("\${kafka.customer.created.notification.topic}")
        topicName: String
    ) : NewTopic {
        return TopicBuilder
            .name(topicName)
            .partitions(3)
            .build()
    }

    @Bean
    fun notifySitterCreatedTopic(
        @Value("\${kafka.sitter.created.notification.topic}")
        topicName: String
    ) : NewTopic {
        return TopicBuilder
            .name(topicName)
            .partitions(3)
            .build()
    }

    @Bean
    fun reviewTopic(
        @Value("\${kafka.review.topic}")
        topicName: String
    ) : NewTopic {
        return TopicBuilder
            .name(topicName)
            .partitions(3)
            .build()
    }
}