package com.samawade.expensetracker.data.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.UserPreferences
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.responses.Users

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences
):BaseRepository() {

    suspend fun registerUser(user: Users) = safeApiCall {
        api.registerUser(user)
    }

    suspend fun login(login: Login ) = safeApiCall {
        api.login(login)
    }

    suspend fun saveAuthToken(token: String, id: String){
        preferences.saveAuthToken(token, id)
    }



}