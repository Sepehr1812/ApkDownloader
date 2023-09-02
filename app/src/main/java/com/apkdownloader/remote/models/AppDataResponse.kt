package com.apkdownloader.remote.models

import com.squareup.moshi.Json

data class AppDataResponse(
    @field:Json(name = "icon") val icon: String,
    @field:Json(name = "packagename") val packageName: String,
    @field:Json(name = "title") val title: String,
    @field:Json(name = "url") val url: String
)
