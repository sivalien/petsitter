package com.sitter.internal.controller

import com.sitter.internal.controller.dto.SitterView
import com.sitter.internal.repository.dto.SitterFilter
import com.sitter.internal.service.SitterService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/sitter", produces = ["application/json"])
class SitterController(
    private val sitterService: SitterService
) {
    @GetMapping("")
    fun getByFilter(sitterFilter: SitterFilter) : ResponseEntity<List<SitterView>> {
        return ResponseEntity.ok(sitterService.getByFilter(sitterFilter))
    }
}