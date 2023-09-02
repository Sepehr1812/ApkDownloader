package com.apkdownloader.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apkdownloader.ui.theme.ApkDownloaderTheme
import com.apkdownloader.viewmodels.ApkDownloaderViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val apkDownloaderViewModel = hiltViewModel<ApkDownloaderViewModel>()

            // displaying error message
            val errorState by apkDownloaderViewModel.errorState.collectAsStateWithLifecycle()
            if (errorState.isNotEmpty())
                Toast.makeText(LocalContext.current, errorState, Toast.LENGTH_LONG).show()

            val appDataState by apkDownloaderViewModel.appDataState.collectAsStateWithLifecycle()

            ApkDownloaderTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (appDataState != null)
                        AppDownloadScreen(appDataState!!)
                }
            }
        }
    }
}
