package com.kani.nytimespopular.utils

sealed class Response<T> {

    data class Result<T>(val data: T): Response<T>()

    data class Error<T>(val throwable: Throwable): Response<T>()
}
