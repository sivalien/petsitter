package com.PetSitter.controller

import com.PetSitter.controller.dto.response.CustomerOrdersResponse
import com.PetSitter.controller.dto.response.SitterOrdersResponse
import com.PetSitter.service.OrderService
import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.controller.dto.response.FullUserResponse
import com.PetSitter.controller.dto.response.SitterResponse
import com.PetSitter.repository.dto.Order
import com.PetSitter.repository.dto.OrderStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@Controller
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {
    @PatchMapping("/{order_id}/accept")
    fun confirmOrder(
        @PathVariable("order_id") orderId: Long,
        @RequestAttribute("uid") userId: Long,
    ) : ResponseEntity<FullUserResponse> {
        return ResponseEntity.ok(orderService.acceptOrder(orderId, userId))
    }

    @PatchMapping("/{order_id}/reject")
    fun rejectOrder(
        @PathVariable("order_id") orderId: Long,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<String> {
        orderService.rejectOrder(orderId, userId)
        return ResponseEntity.ok("Order is successfully rejected")
    }

    @GetMapping("")
    fun getOrderByUserId(
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<List<FullOrderResponse>> {
        return ResponseEntity.ok(orderService.getByUserId(userId).map { FullOrderResponse(
            it.id,
            it.customer.toCustomerView(it.customerUser),
            it.sitter.toSitterView(it.sitterUser),
            it.status,
            it.updated,
            it.created
        ) })
    }

    @GetMapping("/customer")
    fun getCustomerOrdersByUserId(
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<List<CustomerOrdersResponse>> {
        return ResponseEntity.ok(orderService.getCustomerOrdersByUserId(userId))
    }

    @GetMapping("/sitter")
    fun getSitterOrdersByUserId(
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<List<SitterOrdersResponse>> {
        return ResponseEntity.ok(orderService.getSitterOrdersByUserId(userId))
    }

    @PostMapping("/customer/{customer_id}")
    fun orderRequestToCustomer(
        @PathVariable("customer_id") customerId: Long,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<Order> {
        return ResponseEntity.ok(orderService.orderRequestToCustomer(userId, customerId))
    }

    @PostMapping("/sitter/{sitter_id}")
    fun orderRequestToSitter(
        @PathVariable("sitter_id") sitterId: Long,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<Order> {
        return ResponseEntity.ok(orderService.orderRequestToSitter(userId, sitterId))
    }
}

data class FullOrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val sitter: SitterResponse,
    val status: OrderStatus,
    val updated: LocalDateTime,
    val created: LocalDateTime
)
