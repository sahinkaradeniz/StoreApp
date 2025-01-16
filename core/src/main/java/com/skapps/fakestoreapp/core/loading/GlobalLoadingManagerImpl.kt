package com.skapps.fakestoreapp.core.loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GlobalLoadingManagerImpl @Inject constructor() : GlobalLoadingManager {


    private val mutex = Mutex()

    private var globalCount: Int = 0

    private val localMap = mutableMapOf<String, Int>()

    private val partialMap = mutableMapOf<String, Int>()

    private val _activeLoadings = MutableStateFlow<Set<LoadingType>>(emptySet())
    override val activeLoadings: StateFlow<Set<LoadingType>> = _activeLoadings


     override suspend fun show(type: LoadingType) {
        mutex.withLock {
            when (type) {
                is LoadingType.Global -> {
                    globalCount++
                }
                is LoadingType.Local -> {
                    val current = localMap[type.key] ?: 0
                    localMap[type.key] = current + 1
                }
                is LoadingType.Partial -> {
                    val current = partialMap[type.message] ?: 0
                    partialMap[type.message] = current + 1
                }
            }
            updateFlowLocked()
        }
    }


    override suspend fun hide(type: LoadingType) {
        mutex.withLock {
            when (type) {
                is LoadingType.Global -> {
                    if (globalCount > 0) {
                        globalCount--
                    }
                }
                is LoadingType.Local -> {
                    val current = localMap[type.key] ?: 0
                    if (current > 0) {
                        localMap[type.key] = current - 1
                    }
                }
                is LoadingType.Partial -> {
                    val current = partialMap[type.message] ?: 0
                    if (current > 0) {
                        partialMap[type.message] = current - 1
                    }
                }
            }
            updateFlowLocked()
        }
    }

    private fun updateFlowLocked() {
        val newSet = mutableSetOf<LoadingType>()
        println("Global count: $globalCount")
        if (globalCount > 0) {
            println("Global loading is active")
            newSet.add(LoadingType.Global)
        }

        for ((key, count) in localMap) {
            if (count > 0) {
                println("Local loading is active for key: $key")
                newSet.add(LoadingType.Local(key))
            }
        }

        for ((key, count) in partialMap) {
            if (count > 0) {
                println("Partial loading is active for key: $key")
                newSet.add(LoadingType.Partial(key))
            }
        }
        println("Active loadings: $newSet")
        _activeLoadings.value = newSet
    }
}