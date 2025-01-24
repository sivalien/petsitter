package com.sitter.notification.message

import com.sitter.notification.view.CustomerView
import com.sitter.notification.view.SitterView

data class ReviewMessage (
    val requests: List<ReviewRequest>
)

data class ReviewRequest(
    val sitter: SitterView,
    val customer: CustomerView
)