package org.sjhstudio.fastcampus.part2.chapter1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        findViewById<WebView>(R.id.web_view).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl("https://google.com")
        }
    }
}