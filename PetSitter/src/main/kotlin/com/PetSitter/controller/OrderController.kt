package com.PetSitter.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/order", produces = ["application/json"], consumes = ["application/json"])
class OrderController {
    @PostMapping
    fun createOrderAndConsumerAnnouncement() {

    }

    @PostMapping
    fun createOrderAndPetSitterAnnouncement() {

    }

    @PatchMapping("/{order_id}")
    fun confirmOrder() {

    }

    @PatchMapping("/{order_id}")
    fun rejectOrder() {

    }
}