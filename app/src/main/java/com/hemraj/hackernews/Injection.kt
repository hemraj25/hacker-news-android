package com.hemraj.hackernews

import com.hemraj.hackernews.data.HackerNewsDataRepository
import com.hemraj.hackernews.data.network.HackerNewsService

object Injection {

    fun provideHackerNewsRepo() = HackerNewsDataRepository(HackerNewsService.api)
}