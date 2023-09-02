package com.apkdownloader.views

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.apkdownloader.BuildConfig
import com.apkdownloader.R
import com.apkdownloader.models.AppData
import com.apkdownloader.ui.theme.ApkDownloaderTheme
import com.apkdownloader.utils.PathUtil
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.downloader.Status
import java.io.File


@Composable
fun AppDownloadScreen(appData: AppData) {

    val context = LocalContext.current

    var progress by remember { mutableStateOf(0f) }
    var downloadState by remember { mutableStateOf(DownloadState.Ready) }
    var apkFilePath by remember { mutableStateOf("") }
    var downloadId by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // page title
        Text(
            text = if (downloadState == DownloadState.Finished)
                stringResource(R.string.download_page_title_finished)
            else stringResource(R.string.download_page_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        // app icon image
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = rememberAsyncImagePainter(model = appData.icon),
            contentDescription = stringResource(R.string.app_icon_desc),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(128.dp)
        )

        // app title
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = appData.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        // download/install button
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // install the app if the download is finished
                if (downloadState == DownloadState.Finished)
                    installAPK(apkFilePath, context)
                // download the file otherwise
                else onDownloadButtonClick(
                    context,
                    appData.url,
                    downloadState,
                    downloadId,
                    onDownloadStateChange = { downloadState = it },
                    onDownloadIdChange = { downloadId = it },
                    onApkFilePathChange = { apkFilePath = it },
                    onProgressChange = { progress = it },
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = when (downloadState) {
                    DownloadState.Ready -> stringResource(R.string.download_text)
                    DownloadState.Finished -> stringResource(R.string.Install_text)
                    DownloadState.Paused -> stringResource(R.string.resume_text)
                    DownloadState.Downloading -> stringResource(R.string.pause_text)
                }, fontSize = 16.sp
            )

        }

        // progress bar
        Spacer(
            modifier = Modifier
                .height(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), progress = progress)
    }
}

private inline fun onDownloadButtonClick(
    context: Context,
    url: String,
    downloadState: DownloadState,
    downloadId: Int,
    crossinline onDownloadStateChange: (DownloadState) -> Unit,
    onDownloadIdChange: (Int) -> Unit,
    onApkFilePathChange: (String) -> Unit,
    crossinline onProgressChange: (Float) -> Unit
) {

    // checks if the process is already running
    if (Status.RUNNING == PRDownloader.getStatus(downloadId)) {
        // pauses the download
        PRDownloader.pause(downloadId)
        onDownloadStateChange(DownloadState.Paused)
    }

    // checks if the status is paused
    else if (Status.PAUSED == PRDownloader.getStatus(downloadId)) {
        // resume the download if download is paused
        PRDownloader.resume(downloadId)
        onDownloadStateChange(DownloadState.Downloading)
    }

    // checks if download is not started yet
    else if (downloadState == DownloadState.Ready) {

        // getting the filename
        val fileName = URLUtil.guessFileName(url, null, null)
        val path = PathUtil.getRootDirPath(context)
        onApkFilePathChange(path.plus("/").plus(fileName))

        // making the download request
        PRDownloader.download(url, path, fileName)
            .build()
            .setOnStartOrResumeListener {
                onDownloadStateChange(DownloadState.Downloading)
                Toast.makeText(context, "Downloading started", Toast.LENGTH_SHORT).show()
            }
            .setOnPauseListener {
                onDownloadStateChange(DownloadState.Paused)
                Toast.makeText(context, "Downloading Paused", Toast.LENGTH_SHORT).show()
            }
            .setOnProgressListener { p -> // getting the progress of download
                onProgressChange(p.currentBytes.toFloat().div(p.totalBytes.toFloat()))
            }
            .start(object : OnDownloadListener {

                override fun onDownloadComplete() {
                    onDownloadStateChange(DownloadState.Finished)
                    Toast.makeText(context, "Downloading Completed", Toast.LENGTH_SHORT).show()
                }

                override fun onError(error: Error?) {
                    onDownloadStateChange(DownloadState.Ready)
                    Toast.makeText(context, "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }).also { onDownloadIdChange(it) }
    }
}

private fun installAPK(path: String, context: Context) {
    val file = File(path)
    if (file.exists()) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(
            uriFromFile(context, File(path)),
            "application/vnd.android.package-archive"
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
            Toast.makeText(context, "Error in opening file!", Toast.LENGTH_LONG).show()
        }
    } else Toast.makeText(context, "File does not exist!", Toast.LENGTH_LONG).show()
}

private fun uriFromFile(context: Context, file: File): Uri =
    FileProvider.getUriForFile(
        context,
        BuildConfig.APPLICATION_ID + ".provider",
        file
    )

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ApkDownloaderTheme {

        AppDownloadScreen(
            AppData(
                icon = "https://myket.ir/app-icon/ir.mci.ecareapp_251904e9-ca99-45f8-b2b5-af14b47a623e.png",
                packageName = "ir.mci.ecareapp",
                title = "همراه من ",
                url = "https://cdn20.myket.ir//apk-files/farsiapps/ir.mci.ecareapp_50902_70728.apk?md5=QkX53ZLEiPw7gZG9m5iuPw==&expires=1693899701&tag=BA8FF2D978561B032C689232EB1FA2F8&myketVersion=700"
            )
        )
    }
}