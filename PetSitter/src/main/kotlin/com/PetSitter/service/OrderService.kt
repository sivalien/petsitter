package com.PetSitter.service

import com.PetSitter.controller.*
import com.PetSitter.controller.dto.response.CustomerOrdersResponse
import com.PetSitter.controller.dto.response.SitterOrdersResponse
import com.PetSitter.repository.dto.FullOrder
import com.PetSitter.repository.OrderRepository
import com.PetSitter.controller.dto.response.FullUserResponse
import com.PetSitter.controller.dto.response.SitterResponse
import com.PetSitter.repository.dto.Customer
import com.PetSitter.repository.dto.Order
import com.PetSitter.repository.dto.OrderStatus
import com.PetSitter.repository.dto.Sitter
import com.PetSitter.service.dto.OrderConfirmation
import com.PetSitter.service.dto.OrderAction
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val customerService: CustomerService,
    private val sitterService: SitterService,
    private val userService: UserService,
    @Value("\${notification.api.url}")
    private val notificationUrl: String,
    private val restClient: RestClient,
    private val transactionTemplate: TransactionTemplate
) {
    fun getByUserId(userId: Long) : List<FullOrder> {
        return orderRepository.getByUserId(userId).sortedByDescending { it.updated }
    }
    fun getSitterOrdersByUserId(userId: Long) : List<SitterOrdersResponse> {
        return orderRepository.getSitterOrdersByUserId(userId)
            .map { it.toSitterOrdersResponse() }
            .sortedByDescending { it.updated }
    }

    fun getCustomerOrdersByUserId(userId: Long) : List<CustomerOrdersResponse> {
        return orderRepository.getCustomerOrdersByUserId(userId)
            .map { it.toCustomerOrdersResponse() }
            .sortedByDescending { it.updated }
    }

    fun orderRequestToSitter(userId: Long, sitterId: Long) : Order {
        val (result, orderAction) = transactionTemplate.execute {
            val customer = customerService.getEnabledByUserId(userId)
                ?: throw BadRequestException("this user has no customer advert")
            val sitter = sitterService.getById(sitterId)
            if (sitter.advert.userId == userId)
                throw BadRequestException("user cannot be sitter and customer together")

            return@execute createOrder(userId, sitter, customer) to OrderAction(
                customer.toCustomerView(userService.getById(customer.userId)),
                SitterResponse(userService.getById(sitter.advert.userId), sitter),
                true
            )
        }!!

        sendToNotificationService("order/request", orderAction)
        return result
    }

    fun orderRequestToCustomer(userId: Long, customerId: Long) : Order {
        val (result, orderAction) = transactionTemplate.execute {
            val sitter = sitterService.getByUserId(userId)
                ?: throw BadRequestException("this user has no sitter advert")
            val customer = customerService.getById(customerId)
            if (customer.advert.userId == userId)
                throw BadRequestException("user cannot be sitter and customer together")
            if (!customer.available)
                throw BadRequestException("Customer is not available")

            return@execute createOrder(userId, sitter, customer) to OrderAction(
                customer.toCustomerView(userService.getById(customer.userId)),
                SitterResponse(userService.getById(sitter.advert.userId), sitter),
                false
            )
        }!!

        sendToNotificationService("order/request", orderAction)
        return result
    }

    private fun createOrder(userId: Long, sitter: Sitter, customer: Customer) : Order {
        if (orderRepository.findBySitterIdAndCustomerId(sitterId=sitter.advert.id, customerId = customer.id) != null)
            throw BadRequestException("this pair of customer and sitter already has an order")
        return orderRepository.create(sitter.advert.id, customer.id, OrderStatus.PENDING, userId)!!
    }

    fun getById(id: Long) : Order = orderRepository.getById(id)
        ?: throw NotFoundException("Order with id=$id not found")

    fun acceptOrder(
        orderId: Long,
        userId: Long
    ) : FullUserResponse {
        val (user, orderConfirmation) = transactionTemplate.execute {
            val order = getById(orderId)
            if (order.status != OrderStatus.PENDING)
                throw BadRequestException("Wrong order status for accept")

            val sitter = sitterService.getById(order.sitterId)
            val customer = customerService.getById(order.customerId)

            val toSitter = order.updatedBy == sitter.advert.userId
            if (toSitter && customer.advert.userId != userId || !toSitter && sitter.advert.userId != userId)
                throw ForbiddenException("this user cannot accept order")

            orderRepository.setStatus(orderId, OrderStatus.ACCEPTED, userId)

            val customerUser = userService.getById(customer.advert.userId)
            customerService.disableCustomer(order.customerId)

            return@execute FullUserResponse(userService.getById(order.updatedBy)) to OrderConfirmation(
                OrderAction(
                    customer.toCustomerView(customerUser),
                    SitterResponse(userService.getById(sitter.advert.userId), sitter),
                    toSitter
                ),
                customerUser.contacts
            )
        }!!

        sendToNotificationService("order/accept", orderConfirmation)
        return user
    }

    fun rejectOrder(
        orderId: Long,
        userId: Long
    ) {
        val orderAction = transactionTemplate.execute {
            val order = getById(orderId)
            if (order.status != OrderStatus.PENDING)
                throw BadRequestException("Wrong order status for rejection")

            val sitter = sitterService.getById(order.sitterId)
            val customer = customerService.getById(order.customerId)

            val toSitter = order.updatedBy == sitter.advert.userId
            if (toSitter && customer.advert.userId != userId || !toSitter && sitter.advert.userId != userId)
                throw ForbiddenException("this user cannot accept order")

            orderRepository.setStatus(orderId, OrderStatus.REJECTED, userId)

            return@execute OrderAction(
                customer.toCustomerView(userService.getById(customer.advert.userId)),
                SitterResponse(userService.getById(sitter.advert.userId), sitter),
                toSitter
            )
        }!!

        sendToNotificationService("order/reject", orderAction)
    }

    private fun <T : Any> sendToNotificationService(uriSuffix: String, data: T){
        val uri = "$notificationUrl/$uriSuffix"
        val response = restClient.post()
            .uri(uri)
            .accept(MediaType.APPLICATION_JSON)
            .body(data)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                throw BadRequestException("Error while sending request to $uri: ${response.statusText}") }
            .toEntity<String>()
        println(response)
    }

}


