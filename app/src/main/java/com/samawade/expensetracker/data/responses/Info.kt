package com.samawade.expensetracker.data.responses

data class Info(
    val __v: Int,
    val _id: String,
    val accountNo: Int,
    val amount: Int,
    val createdAt: String,
    val date: String,
    val description: String,
    val name: String,
    val phone: String,
    val runBalance: Int,
    val type: String,
    val updatedAt: String
)