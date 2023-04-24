package com.hemraj.hackernews.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.hemraj.hackernews.BuildConfig

fun String.isValidUrl(): Boolean {
    return this.isNotEmpty() && this.startsWith("https")
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun log(tag: String?, message: String?, throwable: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, message, throwable)
    }
}