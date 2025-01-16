package com.skapps.fakestoreapp.core.base

import androidx.lifecycle.ViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.core.loading.LoadingType

abstract class BaseViewModel<UiState, UiAction, SideEffect>(
    initialState: UiState,
    globalLoadingManager: GlobalLoadingManager
) : ViewModel(), VMLoading by vmLoading(globalLoadingManager),
    MVI<UiState, UiAction, SideEffect> by mvi(initialState)