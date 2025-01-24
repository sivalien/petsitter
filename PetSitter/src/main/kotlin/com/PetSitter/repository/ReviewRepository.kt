package com.PetSitter.repository

import com.PetSitter.repository.dto.Review
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class ReviewRepository(
    val jdbcTemplate: JdbcTemplate
) {
    fun create(review: Review) {
        jdbcTemplate.update(
            "insert into review (customer_id, sitter_id, score, comment) values (?, ?, ?, ?)",
            review.customerId,
            review.sitterId,
            review.score,
            review.comment
        )
    }
}