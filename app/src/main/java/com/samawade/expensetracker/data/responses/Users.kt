package com.samawade.expensetracker.data.responses

data class Users(
    val name: String,
    val password: String,
    val phone: String,
    val username: String
)

data class User(
    val name: String,
    val phone: String,
    val username: String
)