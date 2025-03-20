package com.sitter.internal.repository

import com.sitter.internal.repository.dto.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class OrderRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun delete(id: Long) {
        jdbcTemplate.update(
            "delete from orders where id=?",
            id
        )
    }

    fun delete(ids: List<Long>) {
        jdbcTemplate.update(
            "delete from orders where id in (${ids.joinToString(", ") { it.toString() }})"
        )
    }

    fun getByStatusAndEndDate(status: OrderStatus, endDate: LocalDate, limit: Long) : List<FullOrder> {
        val query = "select orders.id as orders_id, sitter_id, customer_id, " +
                "customer.user_id as customer_user_id, sitter.user_id as sitter_user_id, " +
                "customer_users.login as customer_user_login, customer_users.first_name as customer_user_first_name, " +
                "customer_users.last_name as customer_user_last_name, " +
                "customer.title as customer_title, customer.description as customer_description, " +
                "customer.location as customer_location, begin_date, end_date, available, " +
                "customer.attendance_in as customer_attendance_in, customer.attendance_out as customer_attendance_out, " +
                "customer.with_dog as customer_with_dog, customer.with_cat as customer_with_cat, " +
                "customer.with_other as customer_with_other, " +
                "sitter_users.login as sitter_user_login, sitter_users.first_name as sitter_user_first_name, " +
                "sitter_users.last_name as sitter_user_last_name, " +
                "sitter.title as sitter_title, sitter.location as sitter_location, " +
                "sitter.description as sitter_description, is_vet, " +
                "sitter.attendance_in as sitter_attendance_in, sitter.attendance_out as sitter_attendance_out, " +
                "sitter.with_dog as sitter_with_dog, sitter.with_cat as sitter_with_cat, " +
                "sitter.with_other as sitter_with_other " +
                "from customer inner join orders on customer.id = orders.customer_id " +
                "inner join sitter on sitter.id = orders.sitter_id " +
                "inner join users as customer_users on customer_users.id = customer.user_id " +
                "inner join users as sitter_users on sitter_users.id = sitter.user_id " +
                "where status = ? and end_date < ? " +
                "limit ?"
        println(query)
        return jdbcTemplate.query(query, mapper, status, endDate, limit)
    }

    private val mapper = RowMapper<FullOrder> { rs, _ ->
        FullOrder(
            rs.getLong("orders_id"),
            Customer(
                Advert(
                    rs.getLong("customer_id"),
                    User(
                        rs.getLong("customer_user_id"),
                        rs.getString("customer_user_login"),
                        rs.getString("customer_user_first_name"),
                        rs.getString("customer_user_last_name")
                    ),
                    rs.getString("customer_title"),
                    rs.getString("customer_location"),
                    rs.getString("customer_description"),
                    rs.getBoolean("customer_with_dog"),
                    rs.getBoolean("customer_with_cat"),
                    rs.getBoolean("customer_with_other"),
                    rs.getBoolean("customer_attendance_in"),
                    rs.getBoolean("customer_attendance_out")
                ),
                rs.getDate("begin_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getBoolean("available")
            ),
            Sitter(
                Advert(
                    rs.getLong("sitter_id"),
                    User(
                        rs.getLong("sitter_user_id"),
                        rs.getString("sitter_user_login"),
                        rs.getString("sitter_user_first_name"),
                        rs.getString("sitter_user_last_name")
                    ),
                    rs.getString("sitter_title"),
                    rs.getString("sitter_location"),
                    rs.getString("sitter_description"),
                    rs.getBoolean("sitter_with_dog"),
                    rs.getBoolean("sitter_with_cat"),
                    rs.getBoolean("sitter_with_other"),
                    rs.getBoolean("sitter_attendance_in"),
                    rs.getBoolean("sitter_attendance_out")
                ),
                rs.getBoolean("is_vet")
            )
        )
    }
}

