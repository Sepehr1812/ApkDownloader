package com.apkdownloader.views

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.apkdownloader.R
import com.apkdownloader.models.AppData
import com.apkdownloader.ui.theme.ApkDownloaderTheme


@Composable
fun AppDownloadScreen(appData: AppData) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.download_page_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))
        Image(
            painter = rememberAsyncImagePainter(model = appData.icon),
            contentDescription = stringResource(R.string.app_icon_desc),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(128.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = appData.title,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.download_text), fontSize = 16.sp)
        }

        Spacer(
            modifier = Modifier
                .height(16.dp)
                .align(Alignment.CenterHorizontally)
        )
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}

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