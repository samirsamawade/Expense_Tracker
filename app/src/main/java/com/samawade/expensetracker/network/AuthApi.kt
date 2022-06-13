package com.samawade.expensetracker.network

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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