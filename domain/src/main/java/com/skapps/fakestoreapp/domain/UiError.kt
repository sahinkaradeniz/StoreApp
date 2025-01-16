package com.skapps.fakestoreapp.domain

sealed class UiError<out T> {
    data class NoInternet(val message: String? = null) : UiError<Nothing>()

    data class Authentication<T>(val errorBody: T? = null) : UiError<T>()

    data class Server<T>(val errorBody: T? = null) : UiError<T>()

    data class IO(val message: String? = null) : UiError<Nothing>()
}


