package com.apkdownloader.mappers

import com.apkdownloader.models.AppData
import com.apkdownloader.remote.models.AppDataResponse

object AppDataMapper {

    fun toDomain(appDataResponse: AppDataResponse) = appDataResponse.run {
        AppData(icon, packageName, title, url)
    }
}