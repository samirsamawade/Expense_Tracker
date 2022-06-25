package com.samawade.expensetracker.data.network

import com.samawade.expensetracker.Model.Login
import com.samawade.expensetracker.data.responses.LoginResponse
import com.samawade.expensetracker.data.responses.UserResponse
import com.samawade.expensetracker.data.responses.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

//    @FormUrlEncoded
//    @POST("auth")
//    suspend fun login(
//        @Field("username") username: String,
//        @Field("password") password: String
//    ): LoginResponse

    @POST("users")
    suspend fun registerUser(
        @Body user: Users
    ): UserResponse

    @POST("auth")
    suspend fun login(
        @Body login: Login
    ): LoginResponse

}