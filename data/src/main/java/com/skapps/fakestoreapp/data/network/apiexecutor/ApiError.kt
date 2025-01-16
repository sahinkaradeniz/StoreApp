package com.skapps.fakestoreapp.data.network.apiexecutor

import okhttp3.ResponseBody

sealed class ApiError(val errorMessage: String? = null) {

    data class NoInternet(val message: String? = null) : ApiError(message)

    data class Authentication(val errorBody: ResponseBody? = null) : ApiError()

    data class Server(val errorBody: ResponseBody? = null) : ApiError()

    data class IO(val message: String? = null) : ApiError(message)

}