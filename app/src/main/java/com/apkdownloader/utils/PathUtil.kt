package com.apkdownloader.utils

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat


object PathUtil {

    fun getRootDirPath(context: Context): String =
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState())
            ContextCompat.getExternalFilesDirs(context.applicationContext, null)[0].absolutePath
        else context.applicationContext.filesDir.absolutePath
}