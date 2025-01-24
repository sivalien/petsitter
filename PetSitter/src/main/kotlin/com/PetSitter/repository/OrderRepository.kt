package com.PetSitter.repository

import com.PetSitter.repository.dto.*
import com.PetSitter.view.UserView
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrderRepository(
    private val namedJdbcTemplate: NamedParameterJdbcTemplate,
    private val jdbcTemplate: JdbcTemplate
) {
    fun getById(id: Long) : Order? {
        return jdbcTemplate.query("select * from orders where id=?", mapper, id).singleOrNull()
    }

    fun create(sitterId: Long, customerId: Long, status: OrderStatus, updatedBy: Long) : Order? {
        val now = LocalDateTime.now()
        return namedJdbcTemplate.query(
            "insert into orders (sitter_id, customer_id, status, created, updated) " +
                    "values (:sitterId, :customerId, :status, :now, :now, :updated_by) " +
            "returning id, sitter_id, customer_id, status, created, updated, updated_by",
            mapOf(
                "sitterId" to sitterId,
                "customerId" to customerId,
                "status" to status.toString(),
                "now" to now,
                "updated_by" to updatedBy
            ),
            mapper
        ).singleOrNull()
    }

    fun setStatus(id: Long, status: OrderStatus, updatedBy: Long) {
        jdbcTemplate.update(
            "update orders set status = ?, updated = ?, updated_by = ? where id = ?",
            status.toString(),
            LocalDateTime.now(),
            updatedBy,
            id
        )
    }

    fun findBySitterIdAndCustomerId(sitterId: Long, customerId: Long) : Order? {
        return jdbcTemplate.query(
            "select * from orders where sitter_id=? and customer_id=?",
            mapper,
            sitterId,
            customerId
        ).firstOrNull()
    }

    fun getCustomerOrdersByUserId(userId: Long) : List<CustomerOrders> {
        val query = "select orders.id as order_id, status, created, updated, customer_id, " +
                "sitter_id, sitter.title, sitter.description, sitter.location, is_vet, sitter.user_id, " +
                "sitter.attendance_in, sitter.attendance_out, sitter.with_dog, sitter.with_cat, sitter.with_other, " +
                "login, first_name, last_name " +
                "from orders " +
                "inner join sitter on orders.sitter_id = sitter.id " +
                "inner join customer on orders.customer_id = customer.id " +
                "inner join users on sitter.user_id = users.id " +
                "where customer.user_id = ?"
        return jdbcTemplate.query(query, mapperToCustomerOrders, userId)
    }

    fun getSitterOrdersByUserId(userId: Long) : List<SitterOrders> {
        val query = "select orders.id as order_id, status, created, updated, sitter_id, " +
                "customer_id, customer.title, customer.description, customer.location, customer.user_id, " +
                "customer.attendance_in, customer.attendance_out, begin_date, end_date, available, " +
                "customer.with_dog, customer.with_cat, customer.with_other, " +
                "login, first_name, last_name " +
                "from orders " +
                "inner join sitter on orders.sitter_id = sitter.id " +
                "inner join customer on orders.customer_id = customer.id " +
                "inner join users on customer.user_id = users.id " +
                "where sitter.user_id = ?"
        return jdbcTemplate.query(query, mapperToSitterOrders, userId)
    }

    fun getByUserId(userId: Long) : List<FullOrder> {
        val query = "select orders.id as orders_id, status, created, updated, " +
                "sitter_id, customer_id, customer.user_id, " +
                "customer_users.login, customer_users.first_name, customer_users.last_name, " +
                "customer.title, customer.description, customer.location, " +
                "begin_date, end_date, available, customer.attendance_in, customer.attendance_out, " +
                "customer.with_dog, customer.with_cat, customer.with_other, " +
                "sitter.user_id, sitter_users.login, sitter_users.first_name, sitter_users.last_name, " +
                "sitter.title, sitter.location, sitter.description, is_vet, " +
                "sitter.attendance_in, sitter.attendance_out, sitter.with_dog, sitter.with_cat, sitter.with_other " +
                "from customer inner join orders on customer.id = orders.customer_id " +
                "inner join sitter on sitter.id = orders.sitter_id " +
                "inner join users as customer_users on customer_users.id = customer.user_id " +
                "inner join users as sitter_users on sitter_users.id = sitter.user_id " +
                "where customer.user_id = ? or sitter.user_id = ? "

        return jdbcTemplate.query(query, fullMapper, userId, userId)
    }

    private val mapper = RowMapper<Order> { rs, _ ->
        Order(
            rs.getLong("id"),
            rs.getLong("sitter_id"),
            rs.getLong("customer_id"),
            enumValueOf(rs.getString("status")),
            rs.getObject("created", LocalDateTime::class.java),
            rs.getObject("updated", LocalDateTime::class.java),
            rs.getLong("updated_by")
        )
    }

    private val mapperToCustomerOrders = RowMapper<CustomerOrders> { rs, _ ->
        CustomerOrders(
            rs.getLong("order_id"),
            rs.getLong("customer_id"),
            Sitter(
                Advert(
                    rs.getLong("sitter_id"),
                    rs.getLong("user_id"),
                    rs.getString("title"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getBoolean("with_dog"),
                    rs.getBoolean("with_cat"),
                    rs.getBoolean("with_other"),
                    rs.getBoolean("attendance_in"),
                    rs.getBoolean("attendance_out"),
                ),
                rs.getBoolean("is_vet"),
            ),
            UserView(
                rs.getLong("user_id"),
                rs.getString("login"),
                rs.getString("first_name"),
                rs.getString("last_name")
            ),
            enumValueOf(rs.getString("status")),
            rs.getObject("created", LocalDateTime::class.java),
            rs.getObject("updated", LocalDateTime::class.java)
        )
    }

    private val mapperToSitterOrders = RowMapper<SitterOrders> { rs, _ ->
        SitterOrders(
            rs.getLong("order_id"),
            rs.getLong("sitter_id"),
            Customer(
                Advert(
                    rs.getLong("customer_id"),
                    rs.getLong("user_id"),
                    rs.getString("title"),
                    rs.getString("location"),
                    rs.getString("description"),
                    rs.getBoolean("with_dog"),
                    rs.getBoolean("with_cat"),
                    rs.getBoolean("with_other"),
                    rs.getBoolean("attendance_in"),
                    rs.getBoolean("attendance_out"),
                ),
                rs.getDate("begin_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getBoolean("available")
            ),
            UserView(
                rs.getLong("user_id"),
                rs.getString("login"),
                rs.getString("first_name"),
                rs.getString("last_name")
            ),
            enumValueOf(rs.getString("status")),
            rs.getObject("created", LocalDateTime::class.java),
            rs.getObject("updated", LocalDateTime::class.java)
        )
    }

    private val fullMapper = RowMapper<FullOrder> { rs, _ ->
        FullOrder(
            rs.getLong("id"),
            Customer(
                Advert(
                    rs.getLong("customer_id"),
                    rs.getLong("customer.user_id"),
                    rs.getString("customer.title"),
                    rs.getString("customer.location"),
                    rs.getString("customer.description"),
                    rs.getBoolean("customer.with_dog"),
                    rs.getBoolean("customer.with_cat"),
                    rs.getBoolean("customer.with_other"),
                    rs.getBoolean("customer.attendance_in"),
                    rs.getBoolean("customer.attendance_out"),
                ),
                rs.getDate("begin_date").toLocalDate(),
                rs.getDate("end_date").toLocalDate(),
                rs.getBoolean("available")
            ),
            UserView(
                rs.getLong("customer.user_id"),
                rs.getString("customer_users.login"),
                rs.getString("customer_users.first_name"),
                rs.getString("customer_users.last_name")
            ),
            Sitter(
                Advert(
                    rs.getLong("siter_id"),
                    rs.getLong("sitter.user_id"),
                    rs.getString("sitter.title"),
                    rs.getString("sitter.location"),
                    rs.getString("sitter.description"),
                    rs.getBoolean("sitter.with_dog"),
                    rs.getBoolean("sitter.with_cat"),
                    rs.getBoolean("sitter.with_other"),
                    rs.getBoolean("sitter.attendance_in"),
                    rs.getBoolean("sitter.attendance_out"),
                ),
                rs.getBoolean("is_vet"),
            ),
            UserView(
                rs.getLong("customer.user_id"),
                rs.getString("customer_users.login"),
                rs.getString("customer_users.first_name"),
                rs.getString("customer_users.last_name")
            ),
            enumValueOf(rs.getString("status")),
            rs.getObject("created", LocalDateTime::class.java),
            rs.getObject("updated", LocalDateTime::class.java)
        )
    }
}

