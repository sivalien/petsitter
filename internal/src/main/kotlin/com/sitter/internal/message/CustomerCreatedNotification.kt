package com.sitter.internal.message

import com.sitter.internal.view.CustomerView

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