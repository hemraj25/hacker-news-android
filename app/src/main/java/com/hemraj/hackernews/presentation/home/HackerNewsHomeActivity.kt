package com.hemraj.hackernews.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import com.hemraj.hackernews.R
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.databinding.ActivityHomeBinding
import com.hemraj.hackernews.domain.HackerNews
import com.hemraj.hackernews.presentation.WebViewActivity
import com.hemraj.hackernews.util.*
import org.koin.android.ext.android.inject

class HackerNewsHomeActivity : AppCompatActivity() {

    private val TAG = "HackerNewsHomeActivity"

    lateinit var binding: ActivityHomeBinding

    private val viewModel by inject<HackerNewsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.composeView.setContent {
            RenderHomeScreen()
        }
        initToolbar()
        initViewModel()
        initView()
    }

    @Composable
    fun RenderHomeScreen() {
        if (viewModel.showTypingAnimation.value) {
            PlayLottieAnimation()
        } else {
            ShowNewsList(data = viewModel.newsList) {
                onNewsClick(it)
            }
        }
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            title = getString(R.string.home_screen_title)
        }
    }

    private fun initView() {
        binding.searchView.searchEt.afterTextChange {
            viewModel.getSearchResult(it)
        }
    }

    private fun onNewsClick(hackerNews: HackerNews) {
        if (hackerNews.url.isValidUrl()) {
            startActivity(WebViewActivity.createIntent(this, hackerNews.url))
        } else {
            "Invalid url ${hackerNews.url}".showToast(this)
        }
    }

    private fun initViewModel() {
        viewModel.observeSearchQueryInput()
        viewModel.searchResultLiveData.observe(this) {
            when (it.status) {

                Result.LOADING -> {
                    viewModel.showTypingAnimation.value = true
                }

                Result.SUCCESS -> {
                    it.data?.let { newsList ->
                        log(TAG, "Success: itemCount-> ${newsList.size}")
                        viewModel.newsList = it.data
                    }
                    viewModel.showTypingAnimation.value = false
                }

                Result.ERROR -> {
                    // NO Op
                }

            }
        }
    }
}
