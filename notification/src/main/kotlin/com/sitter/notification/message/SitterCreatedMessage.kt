package com.sitter.notification.message

import com.sitter.notification.view.SitterView

data class SitterCreatedMessage(
    val sitter: SitterView,
    val customers: List<User>
)
