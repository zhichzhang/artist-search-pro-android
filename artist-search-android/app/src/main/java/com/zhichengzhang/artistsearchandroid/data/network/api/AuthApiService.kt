package com.zhichengzhang.artistsearchandroid.data.network.api

import com.zhichengzhang.artistsearchandroid.data.model.auth.login.LoginResponse
import com.zhichengzhang.artistsearchandroid.data.model.auth.logout.LogoutResponse
import com.zhichengzhang.artistsearchandroid.data.model.auth.me.MeResponse
import com.zhichengzhang.artistsearchandroid.data.model.auth.register.RegisterResponse
import com.zhichengzhang.artistsearchandroid.data.model.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthApiService {
    @GET("auth/login/{email}/{password}")
    suspend fun login(
        @Path("email") email: String,
        @Path("password") password: String
    ): LoginResponse

    @GET("auth/register/{fullName}/{email}/{password}")
    suspend fun register(
        @Path("fullName") fullName: String,
        @Path("email") email: String,
        @Path("password") password: String
    ): RegisterResponse

    @GET("auth/logout")
    suspend fun logout(): LogoutResponse

    @GET("auth/me")
    suspend fun getCurrentUser(): MeResponse
}