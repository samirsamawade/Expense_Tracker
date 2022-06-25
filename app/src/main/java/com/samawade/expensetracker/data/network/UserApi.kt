package com.samawade.expensetracker.data.network

import com.samawade.expensetracker.Model.Transaction
import com.samawade.expensetracker.data.responses.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @GET("users/{userID}")
    suspend fun getUser(
        @Path("userID") userId: String
    ): Users

    @PUT("users/{userID}")
    suspend fun updateUser(
        @Path("userID") userId: String,
        @Body user: User
    ): UserResponse

    @DELETE("users/{userID}")
    suspend fun deleteAccount(
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

    @POST("transection")
    suspend fun transaction(
        @Body transaction: Transaction
    ): TransactionResponse

    @PUT("transection/{transactionId}")
    suspend fun updateTransaction(
        @Path("transactionId") transactionId: String,
        @Body transaction: Transaction
    ): TransactionResponse

    @DELETE("transection/{transactionId}")
    suspend fun deleteTransaction(
        @Path("transactionId") transactionId: String
    ): TransactionResponse
}