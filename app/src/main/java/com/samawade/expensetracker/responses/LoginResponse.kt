package com.samawade.expensetracker.responses

data class LoginResponse(
    val id: String,
    val message: String,
    val status: Boolean,
    val token: String
)