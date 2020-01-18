package com.hemraj.hackernews

import androidx.annotation.StringDef

class Result<T> private constructor(@Status val status: String, val data: T?, val error: Exception?) {

    companion object {
        @Retention(AnnotationRetention.SOURCE) @StringDef(SUCCESS, ERROR, LOADING)
        annotation class Status

        const val SUCCESS = "success"
        const val ERROR = "error"
        const val LOADING = "loading"

        fun <T> success(data: T?): Result<T> {
            return Result(SUCCESS, data, null)
        }

        fun <T> error(exception: Exception): Result<T> {
            return Result(ERROR, null, exception)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(LOADING, data, null)
        }
    }
}