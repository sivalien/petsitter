package com.sitter.notification.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(MailSubjectConfiguration::class)
class AppConfiguration