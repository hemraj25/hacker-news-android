package com.hemraj.hackernews

import android.app.Application
import com.hemraj.hackernews.di.hackerNewsRepositoryModule
import com.hemraj.hackernews.di.hackerNewsViewModel
import com.hemraj.hackernews.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HackerNewsApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HackerNewsApplication)

            modules(listOf(networkModule, hackerNewsRepositoryModule, hackerNewsViewModel))
        }
    }
}