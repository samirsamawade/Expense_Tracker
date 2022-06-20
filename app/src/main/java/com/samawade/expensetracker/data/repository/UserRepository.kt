package com.samawade.expensetracker.data.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.UserPreferences
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.network.UserApi

class UserRepository(
    private val api: UserApi
):BaseRepository() {

    suspend fun getUser(userId: String) = safeApiCall {
        api.getUser(userId)
    }

    suspend fun getStatement(userId: String) = safeApiCall {
        api.getStatement(userId)
    }

    suspend fun getAllStatements(userId: String) = safeApiCall {
        api.getAllStatements(userId)
    }


}