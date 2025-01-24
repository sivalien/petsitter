package com.sitter.notification.message

import com.sitter.notification.view.CustomerView

data class CustomerCreatedMessage (
    val customer: CustomerView,
    val sitters: List<User>
)

data class User(
    val id: Long,
    val email: String,
    val firstName: String,
    val lastName: String
)
