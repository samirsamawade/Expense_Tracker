package com.samawade.expensetracker.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.network.AuthApi

class AuthRepository(
    private val api: AuthApi
):BaseRepository() {
    suspend fun login(login: Login ) = safeApiCall {
        api.login(login)
    }
}