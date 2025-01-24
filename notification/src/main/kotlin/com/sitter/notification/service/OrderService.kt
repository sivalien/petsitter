package com.sitter.notification.service

import com.sitter.notification.controller.OrderAccept
import com.sitter.notification.controller.OrderRequest
import com.sitter.notification.message.ReviewMessage
import org.springframework.stereotype.Service

@Service
class OrderService(
    val mailNotificationService: MailNotificationService
) {
    fun handleOrderRequest(orderRequest: OrderRequest) {
        mailNotificationService.notifyOrderCreated(orderRequest)
    }

    fun handleOrderReject(orderRequest: OrderRequest) {
        mailNotificationService.notifyOrderRejected(orderRequest)
    }

    fun handleOrderAccepted(orderAccept: OrderAccept) {
        mailNotificationService.notifyOrderAccepted(orderAccept)
    }

    fun requestOrderReview(reviewMessage: ReviewMessage) {
        mailNotificationService.reviewNotify(reviewMessage)
    }
}