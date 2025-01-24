package com.sitter.internal.message

import com.sitter.internal.view.CustomerView
import com.sitter.internal.view.SitterView

data class ReviewNotification (
    val requests: List<ReviewRequest>
)

data class ReviewRequest(
    val sitter: SitterView,
    val customer: CustomerView
)