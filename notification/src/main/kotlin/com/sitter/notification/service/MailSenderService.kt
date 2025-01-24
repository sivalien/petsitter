package com.sitter.notification.service

import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailSenderService(
    @Value("\${spring.mail.username}")
    val from: String,
    val mailSender: JavaMailSender,
    val templateEngine: TemplateEngine
) {
    fun sendMimeMessage(recipient: String, context: Context, template: String, subject: String) {
        val mimeMessage = mailSender.createMimeMessage()

        mimeMessage.setFrom(from)
        mimeMessage.setRecipients(MimeMessage.RecipientType.TO, recipient)
        mimeMessage.subject = subject
        mimeMessage.setContent(templateEngine.process(template, context), "text/html; charset=utf-8")

        mailSender.send(mimeMessage)
    }
}