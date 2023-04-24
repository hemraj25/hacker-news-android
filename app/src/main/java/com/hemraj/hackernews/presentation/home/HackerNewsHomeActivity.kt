package com.hemraj.hackernews.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.hackernews.R
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.databinding.ActivityHomeBinding
import com.hemraj.hackernews.presentation.WebViewActivity
import com.hemraj.hackernews.util.*
import org.koin.android.ext.android.inject

class HackerNewsHomeActivity : AppCompatActivity() {

    private val TAG = "HackerNewsHomeActivity"

    lateinit var binding: ActivityHomeBinding

    private val viewModel by inject<HackerNewsViewModel>()

    private val hackerNewsAdapter = HackerNewsAdapter {
        it?.url?.let { url ->
            if (url.isValidUrl()) {
                startActivity(WebViewActivity.createIntent(this, url))
            } else {
                "Invalid url $url".showToast(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        initViewModel()
        initView()
        initRecyclerView()
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            title = getString(R.string.home_screen_title)
        }
    }

    private fun initView() {
        binding.searchView.searchEt.afterTextChange {
            if (it.length > 2) {
                viewModel.getSearchResult(it)
                binding.typingAnimationView.stopAnimation()
            } else {
                hackerNewsAdapter.clearData()
                binding.typingAnimationView.startAnimation()
            }
        }
    }

    private fun initViewModel() {
        viewModel.searchResultLiveData.observe(this, Observer {
            when (it.status) {

                Result.LOADING -> {
                    //No op
                }

                Result.SUCCESS -> {
                    it.data?.let { newsList ->
                        log(TAG, "Success: itemCount-> ${newsList.size}")
                        hackerNewsAdapter.setData(newsList)
                    }
                }

                Result.ERROR -> {
                    // NO Op
                }

            }
        })
    }

    private fun initRecyclerView() {
        binding.rvHackerNews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rvHackerNews.adapter = hackerNewsAdapter
    }
}
