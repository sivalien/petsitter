package com.sitter.internal.controller

import com.sitter.internal.view.SitterView
import com.sitter.internal.model.SitterFilter
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