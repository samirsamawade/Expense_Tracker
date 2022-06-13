package com.samawade.expensetracker.data.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.network.AuthApi

class AuthRepository(
    private val api: AuthApi
):BaseRepository() {
    suspend fun login(login: Login ) = safeApiCall {
        api.login(login)
    }
}