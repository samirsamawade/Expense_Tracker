package com.samawade.expensetracker.repository

import com.samawade.expensetracker.network.AuthApi

class AuthRepository(
    private val api: AuthApi
):BaseRepository() {
    suspend fun login(
        username: String,
        password: String
    ) = safeApiCall {
        api.login(username, password)
    }
}