package com.samawade.expensetracker.data.network

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.responses.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

//    @FormUrlEncoded
//    @POST("auth")
//    suspend fun login(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): LoginResponse

    @POST("auth")
    suspend fun login(
        @Body login: Login
    ): LoginResponse
}