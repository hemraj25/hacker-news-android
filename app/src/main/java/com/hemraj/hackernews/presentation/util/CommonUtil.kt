package com.hemraj.hackernews.presentation.util

import android.content.Context
import android.widget.Toast

fun String.isValidUrl(): Boolean {
    return this.isNotEmpty() && this.startsWith("https")
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}