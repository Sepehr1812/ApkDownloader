package com.apkdownloader.remote.api

import com.apkdownloader.remote.models.AppDataResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface AppApi {

    @GET("app")
    suspend fun getAppData(
        @Header("Authorization") authToken: String
    ): Response<AppDataResponse>
}