package com.skapps.fakestoreapp.core.loading

import kotlinx.coroutines.flow.StateFlow

interface GlobalLoadingManager  {
    val activeLoadings: StateFlow<Set<LoadingType>>
    suspend fun show(type: LoadingType)
    suspend fun hide(type: LoadingType)
}