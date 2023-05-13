package com.hemraj.hackernews.domain

import androidx.annotation.Keep

@Keep
data class HackerNews(val title: String,
                      val commentText: String,
                      val author: String,
                      val url: String)