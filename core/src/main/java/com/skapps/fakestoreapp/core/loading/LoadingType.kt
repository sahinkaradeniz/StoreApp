package com.skapps.fakestoreapp.core.loading

sealed class LoadingType {
    data object Global : LoadingType()
    data class Local(val key: String) : LoadingType()
    data class Partial(val message: String) : LoadingType()
}