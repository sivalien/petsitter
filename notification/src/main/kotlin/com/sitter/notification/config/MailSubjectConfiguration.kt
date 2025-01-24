package com.sitter.notification.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "mail.subject")
@ConfigurationPropertiesScan
data class MailSubjectConfiguration @ConstructorBinding constructor (
    val advertCreated: String,
    val orderRequest: String,
    val orderReject: String,
    val orderAccept: String,
    val review: String
)