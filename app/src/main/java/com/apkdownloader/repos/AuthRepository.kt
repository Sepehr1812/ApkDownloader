package com.apkdownloader.repos

import android.util.Log
import com.apkdownloader.remote.api.AuthApi
import com.apkdownloader.remote.executeApi
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApi: AuthApi) {

    suspend fun getAuthData() = executeApi({
        authApi.getAuthData()
    }, caster = { it },
        onError = {
            Log.d("AuthRepository::getAuthData", it.toString())
        })

    suspend fun getAuthToken(answer: Int) = executeApi({
        authApi.getAuthToken(answer)
    }, caster = { it },
        onError = {
            Log.d("AuthRepository::getAuthToken", it.toString())
        })
}