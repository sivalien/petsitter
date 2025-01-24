package com.sitter.internal.model

data class FullOrder(
    val id: Long,
    val customer: Customer,
    val sitter: Sitter
)