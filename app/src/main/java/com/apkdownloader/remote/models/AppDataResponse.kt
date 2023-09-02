package com.apkdownloader.remote.models

import com.squareup.moshi.Json

data class AppDataResponse(
    @Json(name = "icon") val icon: String,
    @Json(name = "packagename") val packageName: String,
    @Json(name = "title") val title: String,
    @Json(name = "url") val url: String
)
