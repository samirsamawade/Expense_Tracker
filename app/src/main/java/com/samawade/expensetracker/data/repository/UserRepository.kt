package com.samawade.expensetracker.data.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.UserPreferences
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.network.UserApi

class UserRepository(
    private val api: UserApi
//    private val preferences: UserPreferences
):BaseRepository() {

    suspend fun getUser(userId: String) = safeApiCall {
        api.getUser(userId)
    }

//    suspend fun saveAuthToken(token: String, id: String){
//        preferences.saveAuthToken(token, id)
//    }
}