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

    @POST("transection")
    suspend fun transaction(
        @Body transaction: Transaction
    ): TransactionResponse

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