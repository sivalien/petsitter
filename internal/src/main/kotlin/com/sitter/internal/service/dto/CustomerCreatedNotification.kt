package com.sitter.internal.service.dto

import com.sitter.internal.controller.dto.CustomerView

data class CustomerCreatedNotification(
    val customer: CustomerView,
    val sitters: List<UserInfo>
)

data class UserInfo(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String
)