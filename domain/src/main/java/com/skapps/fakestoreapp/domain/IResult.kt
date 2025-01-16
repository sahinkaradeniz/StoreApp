package com.skapps.fakestoreapp.domain

sealed class IResult<out T,out IApiErrorModel> {
    class Success<T>(val response: T) : IResult<T, Nothing>()
    class Error<IApiErrorModel>(val error: UiError<IApiErrorModel>) : IResult<Nothing, IApiErrorModel>()

    inline fun <R> mapSuccess(transform: (T) -> R): IResult<R, IApiErrorModel> {
        return when (this) {
            is Success -> Success(transform(response))
            is Error -> Error(error)
        }
    }
}


inline fun <T, IApiErrorModel, R> IResult<T, IApiErrorModel>.toDomain(crossinline transform: (T) -> R): IResult<R, IApiErrorModel> {
    return mapSuccess { transform(it) }
}

inline fun <T, IApiErrorModel> IResult<T, IApiErrorModel>.onSuccess(action: (T) -> Unit): IResult<T, IApiErrorModel> {
    if (this is IResult.Success) action(response)
    return this
}

inline fun <T, IApiErrorModel> IResult<T, IApiErrorModel>.onError(action: (UiError<IApiErrorModel>) -> Unit): IResult<T, IApiErrorModel> {
    if (this is IResult.Error) action(error)
    return this
}

inline fun <T, IApiErrorModel> IResult<T, IApiErrorModel>.onErrorWithMessage(action: (String) -> Unit): IResult<T, IApiErrorModel> {
    if (this is IResult.Error) {
        when (val error = error) {
            is UiError.NoInternet -> action(error.message ?: "No internet connection")
            is UiError.Authentication -> action("Authentication error")
            is UiError.Server -> action("Server error")
            is UiError.IO -> action("IO error")
        }
    }
    return this
}