package com.samawade.expensetracker.data.repository

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.Model.Transaction
import com.samawade.expensetracker.data.UserPreferences
import com.samawade.expensetracker.data.network.AuthApi
import com.samawade.expensetracker.data.network.UserApi
import com.samawade.expensetracker.data.responses.User
import com.samawade.expensetracker.data.responses.Users

class UserRepository(
    private val api: UserApi
) : BaseRepository() {

    suspend fun getUser(userId: String) = safeApiCall {
        api.getUser(userId)
    }

suspend fun updateUser(userId: String, user: User) = safeApiCall {
        api.updateUser(userId, user)
    }

    suspend fun deleteAccount(userId: String) = safeApiCall {
        api.deleteAccount(userId)
    }


    suspend fun getStatement(userId: String) = safeApiCall {
        api.getStatement(userId)
    }

    suspend fun getAllStatements(userId: String) = safeApiCall {
        api.getAllStatements(userId)
    }


    suspend fun transaction(transaction: Transaction) = safeApiCall {
        api.transaction(transaction)
    }

    suspend fun updateTransaction(transactionId: String, transaction: Transaction) = safeApiCall {
        api.updateTransaction(transactionId, transaction)
    }

    suspend fun deleteTransaction(transactionId: String) = safeApiCall {
        api.deleteTransaction(transactionId)
    }


}