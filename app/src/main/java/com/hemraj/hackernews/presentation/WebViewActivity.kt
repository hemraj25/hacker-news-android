package com.hemraj.hackernews.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.*
import com.hemraj.hackernews.databinding.ActivityWebViewBinding
import com.hemraj.hackernews.util.startAnimation
import com.hemraj.hackernews.util.stopAnimation

class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initWebView()
        loadUrl()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun loadUrl() {
        binding.loadingAnimationView.startAnimation()
        binding.webView.loadUrl(intent.getStringExtra(EXTRA_URL)?: "")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        binding.webView.settings.apply {
            javaScriptEnabled = true
            builtInZoomControls = true
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            domStorageEnabled = true
            useWideViewPort = false
            loadWithOverviewMode = false
        }

        binding.webView.apply {
            clearHistory()
            clearCache(true)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    binding.loadingAnimationView.stopAnimation()
                }

                override fun onReceivedError(view: WebView?, request: WebResourceRequest?,
                                             error: WebResourceError?) {
                    binding.loadingAnimationView.stopAnimation()
                }

                override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?,
                                                error: SslError?) {
                    super.onReceivedSslError(view, handler, error)
                    binding.loadingAnimationView.stopAnimation()
                }
            }
        }
    }

    companion object {

        private const val EXTRA_URL = "extra_url"

        fun createIntent(context: Context, url: String): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
            }
        }
    }
}
