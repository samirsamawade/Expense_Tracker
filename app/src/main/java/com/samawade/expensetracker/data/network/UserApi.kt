package com.samawade.expensetracker.data.network

import com.samawade.expensetracker.data.responses.Statement
import com.samawade.expensetracker.data.responses.Statements
import com.samawade.expensetracker.data.responses.TransactionResponse
import com.samawade.expensetracker.data.responses.Users
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{userID}")
    suspend fun getUser(
        @Path("userID") userId: String
    ): Users

    @GET("statements/info/{userID}")
    suspend fun getStatement(
        @Path("userID") userId: String
    ): Statement

    @GET("statements/{userID}")
    suspend fun getAllStatements(
        @Path("userID") userId: String
    ): Statements



    @DELETE("transection/{transactionId}")
    suspend fun deleteTransaction(
        @Path("transactionId") transactionId: String
    ): TransactionResponse
}