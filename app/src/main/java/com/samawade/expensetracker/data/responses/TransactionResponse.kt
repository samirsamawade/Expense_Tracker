package com.samawade.expensetracker.data.responses

data class TransactionResponse(
    val info: InfoX,
    val message: String,
    val status: Boolean
)