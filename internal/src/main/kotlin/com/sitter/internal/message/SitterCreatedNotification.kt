package com.sitter.internal.message

import com.sitter.internal.view.SitterView

data class SitterCreatedNotification(
    val sitter: SitterView,
    val customers: List<UserInfo>
)
