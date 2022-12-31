package com.camarmdev.trickstok

import android.app.DownloadManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val trickstok: WebView?
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trickstok = findViewById<View>(R.id.webview) as WebView
        trickstok.setWebViewClient(WebViewClient())
        trickstok.loadUrl("https://trickstok.camarm.fr/home")
        trickstok.settings.javaScriptEnabled = true
        trickstok.setDownloadListener(DownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val uri = Uri.parse(url)
            val cookie = CookieManager.getInstance().getCookie(url)
            val request = DownloadManager.Request(uri)
            request.setDescription("Votre téléchargement TricksTok a commencé.")
            request.addRequestHeader("Cookie", cookie)
            request.addRequestHeader("User-Agent", userAgent)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setTitle("TricksTok")
            request.setVisibleInDownloadsUi(true)
            (getSystemService(DOWNLOAD_SERVICE) as DownloadManager).enqueue(request)
        })
    }


    class webClient : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

//    override fun onBackPressed() {
//        if (trickstok.canGoBack()) {
//            trickstok.goBack()
//        } else {
//            super.onBackPressed()
//        }
//    }
}