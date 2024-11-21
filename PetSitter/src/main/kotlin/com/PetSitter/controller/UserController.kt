package com.PetSitter.controller

import com.PetSitter.controller.dto.UserRequestDto
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController {
    @PostMapping(value = [""], produces = ["application/json"], consumes = ["application/json"])
    fun register(@RequestBody userRequestDto: UserRequestDto) {

    }
}