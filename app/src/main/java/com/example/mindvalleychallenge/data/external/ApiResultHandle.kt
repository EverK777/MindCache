package com.example.mindvalleychallenge.data.external


sealed class ApiResultHandle<out T> {
    data class Success<out T>(val value:T): ApiResultHandle<T>()
    object NetworkError: ApiResultHandle<Nothing>()
}