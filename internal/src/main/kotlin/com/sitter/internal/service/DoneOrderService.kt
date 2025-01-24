package com.sitter.internal.service

import com.sitter.internal.message.ReviewNotification
import com.sitter.internal.message.ReviewRequest
import com.sitter.internal.model.DoneOrder
import com.sitter.internal.model.FullOrder
import com.sitter.internal.repository.OrderHistoryRepository
import com.sitter.internal.repository.OrderRepository
import com.sitter.internal.model.OrderStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

@Service
class DoneOrderService(
    private val orderRepository: OrderRepository,
    private val orderHistoryRepository: OrderHistoryRepository,
    @Value("\${search.done.orders.interval.in.minutes}")
    private val interval: Long,
    @Value("\${search.done.orders.limit}")
    private val limit: Long,
    private val reviewNotificationKafkaTemplate: KafkaTemplate<String, ReviewNotification>,
    @Value("\${kafka.review.topic}")
    private val reviewTopic: String
) {
    init {
        val executor = Executors.newScheduledThreadPool(3)
        executor.scheduleAtFixedRate(
            { doJob() },
            interval,
            interval,
            TimeUnit.MINUTES
        )
    }

    fun doJob() {
        println("searching done orders")
        var doneOrders = emptyList<FullOrder>()
        try {
            doneOrders = orderRepository.getByStatusAndEndDate(OrderStatus.ACCEPTED, LocalDate.now(), limit)
        } catch (e: RuntimeException) {
            println(e.message)
            println(e.localizedMessage)
            println(e.stackTrace)
        }
        println(doneOrders)
        println(doneOrders.size)
        doneOrders.map {
            orderHistoryRepository.create(
                DoneOrder(
                    it.id,
                    it.sitter.advert.id,
                    it.customer
                )
            )!!
        }
        orderRepository.delete(doneOrders.map { it.id })
        reviewNotificationKafkaTemplate.send(
            reviewTopic,
            ReviewNotification(
                doneOrders.map { ReviewRequest(
                    it.sitter.toSitterView(),
                    it.customer.toCustomerView()
                ) }
            )
        )
    }
}