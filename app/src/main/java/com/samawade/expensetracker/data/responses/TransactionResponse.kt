package com.samawade.expensetracker.data.responses

data class TransactionResponse(
    val __v: Int,
    val _id: String,
    val amount: Int,
    val createdAt: String,
    val date: String,
    val description: String,
    val type: String,
    val updatedAt: String,
    val userID: String
)