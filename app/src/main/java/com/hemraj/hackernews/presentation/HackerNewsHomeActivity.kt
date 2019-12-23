package com.hemraj.hackernews.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hemraj.hackernews.Injection
import com.hemraj.hackernews.R
import com.hemraj.hackernews.hackernews.HackerNewsViewModelFactory
import kotlinx.android.synthetic.main.activity_home.*

class HackerNewsHomeActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this,
        HackerNewsViewModelFactory(Injection.provideHackerNewsRepo())
    ).get(HackerNewsViewModel::class.java)
    }

    val hackerNewsAdaptor = HackerNewsAdaptor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initRecyclerView()
        initViewModel()
        initView()
    }

    private fun initView() {
        searchEt.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                viewModel.getSearchResult(s?.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun initViewModel() {
        viewModel.searchResult.observe(this, Observer {
            hackerNewsAdaptor.setData(it)
        })
    }

    private fun initRecyclerView() {
        rvHackerNews.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvHackerNews.adapter = hackerNewsAdaptor
    }
}
