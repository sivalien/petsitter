package com.PetSitter.domain.repository

import com.PetSitter.domain.model.OrderStatus
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class OrderRepository(
    private val namedJdbcTemplate: NamedParameterJdbcTemplate
) {
    fun create(petSitterId: Long, consumerId: Long) {
        namedJdbcTemplate.update(
            "insert into Orders (pet_sitter_id, consumer_id, status) values (:petSitterId, :consumerId)",
            mapOf(
                "petSitterId" to petSitterId,
                "consumerId" to consumerId,
                "status" to OrderStatus.CREATED
            )
        )
    }

    fun setStatus(id: Long, status: OrderStatus) {
        namedJdbcTemplate.update(
            "update Orders set status = :status where id = :id",
            mapOf(
                "id" to id,
                "status" to status
            )
        )
    }
}