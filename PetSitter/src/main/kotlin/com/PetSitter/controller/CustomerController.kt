package com.PetSitter.controller

import com.PetSitter.controller.dto.request.CustomerRequest
import com.PetSitter.controller.dto.response.CustomerResponse
import com.PetSitter.controller.dto.request.CustomerFilterRequest
import com.PetSitter.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/customer", produces = ["application/json"])
class CustomerController(
    private val customerService: CustomerService
) {
    @PostMapping("", consumes = ["application/json"])
    fun createCustomer(
        @RequestBody consumerAnnouncement: CustomerRequest,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.create(userId, consumerAnnouncement))
    }

    @GetMapping("/filter")
    fun getConsumerAnnouncementByFilter(
        customerFilter: CustomerFilterRequest
    ) : ResponseEntity<List<CustomerResponse>> {
        val byFilter = customerService.getByFilter(customerFilter)
        return ResponseEntity.ok(byFilter)
    }

    @PutMapping("/{customer_id}")
    fun updateCustomer(
        @RequestAttribute("uid") userId: Long,
        @PathVariable("customer_id") customerId: Long,
        @RequestBody customerRequest: CustomerRequest,
    ) : ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.changeCustomer(userId, customerId, customerRequest))
    }

    @DeleteMapping("/{customer_id}")
    fun deleteCustomer(
        @RequestAttribute("uid") userId: Long,
        @PathVariable("customer_id") customerId: Long
    ) : ResponseEntity<String> {
        customerService.deleteCustomer(userId, customerId)
        return ResponseEntity.ok("Customer announcement successfully deleted")
    }

    @GetMapping("")
    fun getCustomerByUserId(@RequestAttribute("uid") userId: Long) : ResponseEntity<List<CustomerResponse>> {
        return ResponseEntity.ok(customerService.getViewByUserId(userId))
    }
}
