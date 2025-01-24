package com.PetSitter.controller

import com.PetSitter.controller.dto.request.ReviewRequest
import com.PetSitter.repository.dto.Review
import com.PetSitter.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/review", produces = ["application/json"])
class ReviewController(
    val reviewService: ReviewService
) {
    @PostMapping(consumes = ["application/json"])
    fun create(
        @RequestBody reviewRequest: ReviewRequest,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<Review> {
        return ResponseEntity.ok(reviewService.create(userId, reviewRequest))
    }
}