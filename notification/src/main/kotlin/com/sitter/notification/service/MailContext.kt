package com.sitter.notification.service

import com.sitter.notification.controller.OrderAccept
import com.sitter.notification.controller.OrderRequest
import com.sitter.notification.message.CustomerCreatedMessage
import com.sitter.notification.message.ReviewMessage
import com.sitter.notification.message.ReviewRequest
import com.sitter.notification.message.SitterCreatedMessage
import com.sitter.notification.view.AdvertView
import com.sitter.notification.view.CustomerView
import com.sitter.notification.view.SitterView
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context

@Component
object MailContext{
    fun getCustomerCreatedContext(message: CustomerCreatedMessage) : Context {
        val context = Context()
        return context.also { setContextByCustomer(it, message.customer) }
    }

    fun getSitterCreatedContext(message: SitterCreatedMessage) : Context {
        val context = Context()
        return context.also { setContextBySitter(it, message.sitter) }
    }

    fun getContextByOrderRequest(orderRequest: OrderRequest) : Context {
        val context = Context()
        return context.also { setContextByOrder(it, orderRequest) }
    }

    fun getAcceptOrderContext(orderAccept: OrderAccept) : Context {
        val context = Context()
        setContextByOrder(context, orderAccept.order)
        return context.also {  it.setVariable("contacts", orderAccept.contacts) }
    }

    fun getReviewContext(reviewRequest: ReviewRequest) : Context {
        val context = Context()
        return context.also { setContextByCustomer(it, reviewRequest.customer) }
    }

    private fun setContextBySitter(context: Context, sitterView: SitterView) {
        context.setVariable("is_vet", if (sitterView.isVet) "Да" else "Нет")
        setContextByAdvert(context, sitterView.advert)
    }

    private fun setContextByCustomer(context: Context, customerView: CustomerView) {
        context.setVariable("date", "${customerView.beginDate}-${customerView.endDate}")
        setContextByAdvert(context, customerView.advert)
    }

    private fun setContextByAdvert(context: Context, advertView: AdvertView) {
        context.setVariable("name", advertView.user.firstName + " " + advertView.user.lastName)
        context.setVariable("location", advertView.location)
        context.setVariable("description", advertView.description)
        context.setVariable("animal_types", advertView.animalTypes)
    }

    private fun setContextByOrder(context: Context, orderRequest: OrderRequest) {
        if (orderRequest.toSitter) {
            setContextByCustomer(context, orderRequest.customer)
            context.setVariable("first_name", orderRequest.sitter.advert.user.firstName)
        } else {
            setContextBySitter(context, orderRequest.sitter)
            context.setVariable("first_name", orderRequest.customer.advert.user.firstName)
        }
    }

    fun setFirstName(context: Context, firstName: String) =
        context.setVariable("first_name", firstName)
}