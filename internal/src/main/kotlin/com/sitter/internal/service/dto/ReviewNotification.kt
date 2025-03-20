package com.sitter.internal.service.dto

import com.sitter.internal.controller.dto.CustomerView
import com.sitter.internal.controller.dto.SitterView

data class ReviewNotification (
    val requests: List<ReviewRequest>
)

data class ReviewRequest(
    val sitter: SitterView,
    val customer: CustomerView
)