package com.PetSitter.controller

import com.PetSitter.controller.dto.response.AuthenticationResponse
import com.PetSitter.controller.dto.response.UserResponse
import com.PetSitter.controller.dto.request.RegisterRequest
import com.PetSitter.controller.dto.request.LoginRequest
import com.PetSitter.controller.dto.request.UserRequest
import com.PetSitter.service.AuthenticationService
import com.PetSitter.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user", produces = ["application/json"])
class UserController(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/register", consumes = ["application/json"])
    fun register(@RequestBody registerRequest: RegisterRequest) : ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.register(registerRequest))
    }

    @PostMapping("/login", consumes = ["application/json"])
    fun login(@RequestBody loginRequest: LoginRequest)
    : ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest))
    }

    @GetMapping("")
    fun getUserInfo(@RequestAttribute("uid") userId: Long) : ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getById(userId).toUserResponse())
    }

    @PutMapping("", consumes = ["application/json"])
    fun changeUserInfo(
        @RequestBody userRequest: UserRequest,
        @RequestAttribute("uid") userId: Long
    ) : ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.changeUserInfo(userId, userRequest).toUserResponse())
    }

//    @PostMapping("/refresh")
//    fun refresh(@RequestBody refreshTokenRequest: RefreshTokenRequest) :
}

