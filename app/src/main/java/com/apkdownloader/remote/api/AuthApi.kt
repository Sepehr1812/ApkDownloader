package com.apkdownloader.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthApi {

    @GET("v2/data")
    suspend fun getAuthData(): Response<List<Int>>

    @GET("v2/authorization/{answer}")
    suspend fun getAuthToken(
        @Path("answer") answer: Int
    ): Response<String>
}