package com.samawade.expensetracker.data.responses

data class LoginResponse(
    val id: String?,
    val message: String,
    val status: Boolean,
    val token: String?
)