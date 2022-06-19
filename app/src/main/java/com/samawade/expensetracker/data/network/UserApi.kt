package com.samawade.expensetracker.data.network

import com.samawade.expensetracker.data.responses.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("users/{userID}")
    suspend fun getUser(
        @Path("userID") userId: String
    ): Users
}