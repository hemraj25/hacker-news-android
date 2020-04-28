package com.hemraj.hackernews.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.hackernews.R
import com.hemraj.hackernews.Result
import com.hemraj.hackernews.presentation.util.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.address_search_container.*
import org.koin.android.ext.android.inject

class HackerNewsHomeActivity : AppCompatActivity() {

    private val TAG = HackerNewsHomeActivity::class.java.canonicalName

    private val viewModel by inject<HackerNewsViewModel>()

    private val hackerNewsAdaptor = HackerNewsAdaptor {
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
        setContentView(R.layout.activity_home)
        initToolbar()
        initViewModel()
        initView()
        initRecyclerView()
    }

    private fun initToolbar() {
        supportActionBar?.apply {
            title = getString(R.string.home_screen_tiltle)
        }
    }

    private fun initView() {
        searchEt.afterTextChange {
            if (it.length > 2) {
                viewModel.getSearchResult(it)
                typingAnimationView.stopAnimation()
            } else {
                hackerNewsAdaptor.clearData()
                typingAnimationView.startAnimation()
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
                        Log.d(TAG, "Success: itemCount-> ${newsList.size}")
                        hackerNewsAdaptor.setData(newsList)
                    }
                }

                Result.ERROR -> {
                    // NO Op
                }

            }
        })
    }

    private fun initRecyclerView() {
        rvHackerNews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHackerNews.adapter = hackerNewsAdaptor
    }
}
