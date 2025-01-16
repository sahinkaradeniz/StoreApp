package com.skapps.fakestoreapp.core.base

import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.core.loading.LoadingType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

class VMLoadingDelegate internal constructor(
    private val loadingManager: GlobalLoadingManager
) : VMLoading {
    override val activeLoadings: StateFlow<Set<LoadingType>>
        get() = loadingManager.activeLoadings

    override suspend fun <T> doWithLoading(
        loadingType: LoadingType,
        block: suspend () -> T
    ): Result<T> {
        loadingManager.show(loadingType)
        return try {
            val result = block()
            Result.success(result)
        } catch (e: Throwable) {
            Result.failure(e)
        } finally {
            loadingManager.hide(loadingType)
        }
    }

    override suspend fun <T> doWithGlobalLoading(block: suspend () -> T): Result<T> {
        return doWithLoading(LoadingType.Global, block)
    }

    override suspend fun <T> doWithLocalLoading(
        block: suspend () -> T,
        viewKey: String
    ): Result<T> {
        return doWithLoading(LoadingType.Local(key = viewKey), block)
    }

    override suspend fun <T> doWithPartialLoading(
        block: suspend () -> T,
        message: String
    ): Result<T> {
        return doWithLoading(LoadingType.Partial(message = message), block)
    }

    override suspend fun showGlobalLoading() {
        loadingManager.show(LoadingType.Global)
    }

    override suspend fun hideGlobalLoading() {
        loadingManager.hide(LoadingType.Global)
    }


}
fun vmLoading(loadingManager: GlobalLoadingManager): VMLoading = VMLoadingDelegate(loadingManager)