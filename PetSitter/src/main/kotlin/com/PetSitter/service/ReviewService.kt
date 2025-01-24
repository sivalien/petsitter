package com.PetSitter.service

import com.PetSitter.controller.dto.request.ReviewRequest
import com.PetSitter.repository.dto.Review
import com.PetSitter.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(
    val reviewRepository: ReviewRepository
) {
    fun create(userId: Long, reviewRequest: ReviewRequest) : Review {
        val review = Review(userId, reviewRequest.sitterId, reviewRequest.score, reviewRequest.comment)
        reviewRepository.create(review)
        return review
    }
}