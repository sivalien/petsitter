package com.sitter.internal.repository

import com.sitter.internal.model.DoneOrder
import com.sitter.internal.model.OrderHistory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class OrderHistoryRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun create(doneOrder: DoneOrder) : OrderHistory? {
        val query = "insert into order_history " +
                "(customer_id, sitter_id, customer_title, location, customer_description, begin_date, end_date, " +
                "attendance_in, attendance_out, with_dog, with_cat, with_other) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" +
                "returning " +
                "id, customer_id, sitter_id, customer_title, location, customer_description, begin_date, " +
                "end_date, attendance_in, attendance_out, with_dog, with_cat, with_other"
        return jdbcTemplate.query(
            query,
            mapper,
            doneOrder.customer.advert.id,
            doneOrder.sitterId,
            doneOrder.customer.advert.title,
            doneOrder.customer.advert.location,
            doneOrder.customer.advert.description,
            doneOrder.customer.beginDate,
            doneOrder.customer.endDate,
            doneOrder.customer.advert.attendanceIn,
            doneOrder.customer.advert.attendanceOut,
            doneOrder.customer.advert.withDog,
            doneOrder.customer.advert.withCat,
            doneOrder.customer.advert.withOther
        ).singleOrNull()
    }

    private val mapper = RowMapper<OrderHistory> {rs, _ ->
        OrderHistory(
            rs.getLong("id"),
            rs.getLong("sitter_id"),
            rs.getLong("customer_id"),
            rs.getString("customer_title"),
            rs.getString("location"),
            rs.getString("customer_description"),
            rs.getDate("begin_date").toLocalDate(),
            rs.getDate("end_date").toLocalDate(),
            rs.getBoolean("with_dog"),
            rs.getBoolean("with_cat"),
            rs.getBoolean(".with_other"),
            rs.getBoolean("attendance_in"),
            rs.getBoolean("attendance_out")
        )
    }
}