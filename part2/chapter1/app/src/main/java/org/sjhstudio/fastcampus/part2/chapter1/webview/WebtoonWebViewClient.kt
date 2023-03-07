package org.sjhstudio.fastcampus.part2.chapter1.webview

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible

class WebtoonWebViewClient(
    private val progressBar: ProgressBar? = null,
    private val saveHistory: (String) -> Unit
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // https://comic.naver.com/webtoon/detail?titleId=648419&no=375
        val url = request?.url.toString()
        if (url.contains("comic.naver.com/webtoon/detail")) saveHistory.invoke(url)

        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        progressBar?.isVisible = true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBar?.isVisible = false
    }
}