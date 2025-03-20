package com.sitter.internal.service.dto

import com.sitter.internal.controller.dto.SitterView

data class SitterCreatedNotification(
    val sitter: SitterView,
    val customers: List<UserInfo>
)
