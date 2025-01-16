package com.skapps.fakestoreapp.data.network.apiexecutor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response


interface ApiExecutor {
    suspend fun <T> execute(
        call: suspend () -> Response<T>
    ): ApiResult<T>
}