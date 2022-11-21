package com.example.webviewsample

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.webviewsample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnUrl.setOnClickListener {
            openUrl()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun openUrl() {
        if (binding.etUrl.text!!.isEmpty()) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
            return
        }
        var url = binding.etUrl.text.toString()

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://$url"
        }

        webView = binding.webView

        webView.apply {
            webView.loadUrl(url)
            webViewClient = SSLTolerentWebViewClient()
            settings.javaScriptEnabled = true
        }
        binding.btnUrl.visibility = View.GONE
        binding.etUrl.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            binding.btnUrl.visibility = View.VISIBLE
            binding.etUrl.visibility = View.VISIBLE
            super.onBackPressed()
        }
    }

    private fun onReceivedSslError(view: WebView?, handler: SslErrorHandler) {
        handler.proceed() // Ignore SSL certificate errors
    }
}

internal class SSLTolerentWebViewClient : WebViewClient() {
    @SuppressLint("WebViewClientOnReceivedSslError")
    override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        handler.proceed() // Ignore SSL certificate errors
    }
}