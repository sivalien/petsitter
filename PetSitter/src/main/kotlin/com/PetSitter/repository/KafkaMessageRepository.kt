package com.PetSitter.repository

import com.PetSitter.repository.dto.KafkaMessage
import com.PetSitter.repository.dto.KafkaMessageStatus
import com.PetSitter.repository.dto.KafkaMessageType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class KafkaMessageRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun create(messageType: KafkaMessageType, message: ByteArray, status: KafkaMessageStatus) {
        jdbcTemplate.update(
            "insert into kafka_message (message_type, message, status) values (?, ?, ?)",
            messageType.toString(),
            message,
            status.toString()
        )
    }

    fun getByStatus(status: KafkaMessageStatus) : List<KafkaMessage> {
        return jdbcTemplate.query(
            "select * from kafka_message where status = ?",
            mapper,
            status.toString()
        )
    }

    fun setStatusById(id: Long, status: KafkaMessageStatus) {
        jdbcTemplate.update(
            "update kafka_message set status = ? where id = ?",
            status.toString(),
            id,
        )
    }

    val mapper = RowMapper<KafkaMessage> {rs, _ ->
        KafkaMessage(
            rs.getLong("id"),
            KafkaMessageType.valueOf(rs.getString("message_type")),
            rs.getBytes("message"),
            KafkaMessageStatus.valueOf(rs.getString("status"))
        )
    }
}