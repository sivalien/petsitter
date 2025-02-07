package com.PetSitter.repository

import com.PetSitter.repository.dto.Advert
import com.PetSitter.repository.dto.Customer
import com.PetSitter.service.dto.CustomerDto
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class CustomerRepository(
    private val namedJdbcTemplate: NamedParameterJdbcTemplate,
    private val jdbcTemplate: JdbcTemplate
) {
    fun create(customerDto: CustomerDto) : Customer? {
        val namedParameters = mapOf(
            "user_id" to customerDto.advert.userId,
            "title" to customerDto.advert.title,
            "location" to customerDto.advert.location,
            "description" to customerDto.advert.description,
            "date_begin" to customerDto.beginDate,
            "date_end" to customerDto.endDate,
            "attendance_in" to customerDto.advert.attendanceIn,
            "attendance_out" to customerDto.advert.attendanceOut,
            "with_dog" to customerDto.advert.withDog,
            "with_cat" to customerDto.advert.withCat,
            "with_other" to customerDto.advert.withOther
        )
        return namedJdbcTemplate.query(
            "insert into customer " +
            "(begin_date, end_date, user_id, title, location, description, attendance_in, attendance_out, with_dog, with_cat, with_other) " +
            "values (:date_begin, :date_end, :user_id, :title, :location, :description, :attendance_in, :attendance_out, :with_dog, :with_cat, :with_other)" +
            "returning id, begin_date, end_date, user_id, title, location, description, attendance_in, attendance_out, with_dog, with_cat, with_other, available",
            namedParameters,
            mapper
        ).singleOrNull()
    }

    fun findById(announcementId: Long) : Customer? {
        return namedJdbcTemplate.query(
            "select * from customer where id = :id",
            mapOf("id" to announcementId),
            mapper
        ).singleOrNull()
    }

    fun getByUserIdAndAvailable(userId: Long, available: Boolean) : Customer? {
        return jdbcTemplate.query(
            "select * from customer where user_id=? and available=?",
            mapper,
            userId,
            available
        ).firstOrNull()
    }

    fun setAvailable(id: Long, value: Boolean) {
        jdbcTemplate.update("update customer set available=? where id=?", value, id)
    }

    fun updateCustomer(customerId: Long, customer: CustomerDto) : Customer? {
        return jdbcTemplate.query(
            "update customer " +
                    "set location=?, description=?, begin_date=?, end_date=?, attendance_in=?, attendance_out=?, " +
                    "with_dog=?, with_cat=?, with_other=? where id=? " +
                    "returning id, begin_date, end_date, user_id, location, description, attendance_in, " +
                    "attendance_out, with_dog, with_cat, with_other, available",
            mapper,
            customer.advert.location, customer.advert.description, customer.beginDate, customer.endDate,
            customer.advert.attendanceIn, customer.advert.attendanceOut,
            customer.advert.withDog, customer.advert.withCat, customer.advert.withOther,
            customerId
        ).singleOrNull()
    }

    fun deleteCustomer(customerId: Long) {
        jdbcTemplate.update("delete from customer where id=?", customerId)
    }

    private val mapper = RowMapper<Customer> { rs, _ ->
        Customer(
            Advert(
                rs.getLong("id"),
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
        )
    }
}
