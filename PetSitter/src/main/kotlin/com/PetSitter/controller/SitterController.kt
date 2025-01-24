package com.PetSitter.controller

import com.PetSitter.controller.dto.request.SitterRequest
import com.PetSitter.controller.dto.response.SitterResponse
import com.PetSitter.repository.dto.SitterFilter
import com.PetSitter.service.SitterService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/sitter", produces = ["application/json"])
class SitterController(
    private val sitterService: SitterService
) {

    @PostMapping("", consumes = ["application/json"])
    fun createSitter(
        @RequestBody petSitterAnnouncement: SitterRequest,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<SitterResponse> {
        return ResponseEntity.ok(sitterService.create(userId, petSitterAnnouncement))
    }

    @GetMapping("/filter")
    fun getSitterByFilter(
        sitterFilter: SitterFilter
    ) : ResponseEntity<List<SitterResponse>> {
        return ResponseEntity.ok(sitterService.getByFilter(sitterFilter))
    }

    @PutMapping("/{sitter_id}")
    fun updateSitter(
        @RequestAttribute("uid") userId: Long,
        @PathVariable("sitter_id") sitterId: Long,
        @RequestBody sitterRequest: SitterRequest,
    ) : ResponseEntity<SitterResponse> {
        return ResponseEntity.ok(sitterService.updateSitter(userId, sitterId, sitterRequest))
    }

    @DeleteMapping("/{sitter_id}")
    fun delete(
        @RequestAttribute("uid") userId: Long,
        @PathVariable("sitter_id") sitterId: Long,
    ) : ResponseEntity<String> {
        sitterService.delete(userId, sitterId)
        return ResponseEntity.ok("Sitter successfully deleted")
    }

    @GetMapping("")
    fun getByUserId(@RequestAttribute("uid") userId: Long) : ResponseEntity<List<SitterResponse>> {
        return ResponseEntity.ok(sitterService.getViewByUserId(userId))
    }
}