package com.samawade.expensetracker.data.responses

data class Statement(
    val balance: Int,
    val userExpense: Int,
    val userincome: Int
)