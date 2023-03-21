package org.sjhstudio.fastcampus.part2.chapter5

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import org.sjhstudio.fastcampus.part2.chapter5.databinding.ActivityWebViewBinding
import org.sjhstudio.fastcampus.part2.chapter5.ui.MainActivity.Companion.NEWS_URL

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initWebView()

        val url = intent.getStringExtra(NEWS_URL)

        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "잘못된 URL입니다.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            binding.webView.loadUrl(url)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        with(binding.webView) {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
        }
    }
}