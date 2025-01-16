package com.skapps.fakestoreapp.data.mapper

import com.google.gson.Gson
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiError
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiResult
import com.skapps.fakestoreapp.domain.UiError
import com.skapps.fakestoreapp.domain.IResult

inline fun <reified E> parseError(apiResult: ApiResult.Error): IResult.Error<E> {
    return when (apiResult.error) {
        is ApiError.Server -> {
            try {
                val error = Gson().fromJson(
                    apiResult.error.errorBody?.string(),
                    E::class.java
                )
                IResult.Error(UiError.Server(error))
            } catch (e: Exception) {
                IResult.Error(UiError.Server())
            }
        }

        is ApiError.Authentication -> {
            try {
                val error = Gson().fromJson(
                    apiResult.error.errorBody?.string(),
                    E::class.java
                )
                IResult.Error(UiError.Authentication(error))
            } catch (e: Exception) {
                IResult.Error(UiError.Authentication())
            }
        }
        is ApiError.IO -> IResult.Error(UiError.IO(message = apiResult.error.message))
        is ApiError.NoInternet -> IResult.Error(UiError.NoInternet(message = apiResult.error.message))
    }
}
