package com.apkdownloader.repos

import android.util.Log
import com.apkdownloader.mappers.AppDataMapper
import com.apkdownloader.remote.api.AppApi
import com.apkdownloader.remote.executeApi
import javax.inject.Inject

class AppRepository @Inject constructor(private val appApi: AppApi) {

    suspend fun getAppData(authToken: String) = executeApi({
        appApi.getAppData(authToken)
    }, caster = { AppDataMapper.toDomain(it) },
        onError = {
            Log.d("AppRepository::getAppData", it.toString())
        })
}