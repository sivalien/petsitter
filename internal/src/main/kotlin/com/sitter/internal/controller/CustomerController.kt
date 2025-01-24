package com.sitter.internal.controller

import com.sitter.internal.view.CustomerView
import com.sitter.internal.model.CustomerFilter
import com.sitter.internal.service.CustomerService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/customer", produces = ["application/json"])
class CustomerController(
    private val customerService: CustomerService
) {
    @GetMapping("")
    fun getCustomers(filter: CustomerFilter) : ResponseEntity<List<CustomerView>> {
        return ResponseEntity.ok(customerService.getByFilter(filter))
    }
}