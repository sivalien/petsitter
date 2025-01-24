package com.sitter.notification.service

import com.sitter.notification.config.MailSubjectConfiguration
import com.sitter.notification.controller.OrderAccept
import com.sitter.notification.controller.OrderRequest
import com.sitter.notification.message.CustomerCreatedMessage
import com.sitter.notification.message.ReviewMessage
import com.sitter.notification.message.SitterCreatedMessage
import com.sitter.notification.message.User
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context

@Service
class MailNotificationService(
    private val mailSenderService: MailSenderService,
    val mailSubjectConfiguration: MailSubjectConfiguration
) {

    fun notifyCustomerCreated(message: CustomerCreatedMessage) {
        notifyUsersAdvertCreated(
            MailContext.getCustomerCreatedContext(message),
            "customer_created.html",
            message.sitters
        )
    }

    fun notifySitterCreated(message: SitterCreatedMessage) {
        notifyUsersAdvertCreated(
            MailContext.getSitterCreatedContext(message),
            "sitter_created.html",
            message.customers
        )
    }

    private fun notifyUsersAdvertCreated(context: Context, template: String, users: List<User>) {
        for (user in users) {
            MailContext.setFirstName(context, user.firstName)
            mailSenderService.sendMimeMessage(
                "sivalien@mail.ru", // user.email,
                context,
                template,
                mailSubjectConfiguration.advertCreated
            )
        }
    }

    fun notifyOrderCreated(orderRequest: OrderRequest) {
        mailSenderService.sendMimeMessage(
            "sivalien@mail.ru", // if (orderRequest.toSitter) orderRequest.sitter.advert.user.login else orderRequest.sitter.advert.user.login,
            MailContext.getContextByOrderRequest(orderRequest),
            if (orderRequest.toSitter) "order_request_from_customer.html" else "order_request_from_sitter.html",
            mailSubjectConfiguration.orderRequest
        )
    }

    fun notifyOrderRejected(orderRequest: OrderRequest) {
        mailSenderService.sendMimeMessage(
            "sivalien@mail.ru",
            MailContext.getContextByOrderRequest(orderRequest),
            if (orderRequest.toSitter) "order_reject_from_customer.html" else "order_reject_from_sitter.html",
            mailSubjectConfiguration.orderReject
        )
    }

    fun notifyOrderAccepted(orderAccept: OrderAccept) {
        mailSenderService.sendMimeMessage(
            "sivalien@mail.ru",
            MailContext.getAcceptOrderContext(orderAccept),
            if (orderAccept.order.toSitter) "order_accept_from_customer.html" else "order_accept_from_sitter.html",
            mailSubjectConfiguration.orderAccept
        )
    }

    fun reviewNotify(reviewMessage: ReviewMessage) {
        reviewMessage.requests.forEach {
            mailSenderService.sendMimeMessage(
                "sivalien@mail.ru",
                MailContext.getReviewContext(it),
                "review.html",
                mailSubjectConfiguration.review
            )
        }
    }
}