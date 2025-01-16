package com.skapps.fakestoreapp.core.base

import com.skapps.fakestoreapp.core.loading.LoadingType
import kotlinx.coroutines.flow.StateFlow

interface VMLoading {
    val activeLoadings: StateFlow<Set<LoadingType>>
    suspend fun <T> doWithLoading(
        loadingType: LoadingType,
        block: suspend () -> T
    ): Result<T>

    suspend fun <T> doWithGlobalLoading(block: suspend () -> T): Result<T>


    suspend fun <T> doWithLocalLoading(
        block: suspend () -> T,
        viewKey: String
    ): Result<T>

    suspend fun <T> doWithPartialLoading(
        block: suspend () -> T,
        message: String
    ): Result<T>

    suspend fun showGlobalLoading()

    suspend fun hideGlobalLoading()
}