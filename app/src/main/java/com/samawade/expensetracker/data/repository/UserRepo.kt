package com.samawade.expensetracker.data.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.UserPreferences
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.responses.Users

class UserRepo(
    private val api: AuthApi
):BaseRepository() {

    suspend fun registerUser(user: Users) = safeApiCall {
        api.registerUser(user)
    }

}