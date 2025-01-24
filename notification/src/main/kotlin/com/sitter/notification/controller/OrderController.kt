package com.sitter.notification.controller

import com.fasterxml.jackson.annotation.JsonProperty
import com.sitter.notification.service.OrderService
import com.sitter.notification.view.CustomerView
import com.sitter.notification.view.SitterView
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/order")
class OrderController(
    val orderService: OrderService
) {
    @PostMapping("/request", consumes = ["application/json"])
    fun notifyOrderCreated(
        @RequestBody orderRequest: OrderRequest
    ) : ResponseEntity<String> {
        println(orderRequest)
        orderService.handleOrderRequest(orderRequest)
        return ResponseEntity.ok("Notification was sent")
    }

    @PostMapping("/accept")
    fun notifyOrderAccepted(
        @RequestBody orderAccept: OrderAccept
    ) : ResponseEntity<String> {
        println(orderAccept)
        orderService.handleOrderAccepted(orderAccept)
        return ResponseEntity.ok("Notification was sent")
    }

    @PostMapping("/reject")
    fun notifyOrderRejected(
        @RequestBody orderRequest: OrderRequest
    ) : ResponseEntity<String> {
        println(orderRequest)
        orderService.handleOrderReject(orderRequest)
        return ResponseEntity.ok("Notification was sent")
    }

}

data class OrderRequest(
    @field:JsonProperty("customer")
    val customer: CustomerView,
    @field:JsonProperty("sitter")
    val sitter: SitterView,
    @field:JsonProperty("to_sitter")
    val toSitter: Boolean
)

data class OrderAccept(
    @field:JsonProperty("order")
    val order: OrderRequest,
    @field:JsonProperty("contacts")
    val contacts: String
)
