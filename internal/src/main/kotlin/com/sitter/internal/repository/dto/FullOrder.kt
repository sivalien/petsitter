package com.sitter.internal.repository.dto

data class FullOrder(
    val id: Long,
    val customer: Customer,
    val sitter: Sitter
)