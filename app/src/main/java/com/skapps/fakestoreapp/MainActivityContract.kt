package com.skapps.fakestoreapp

import androidx.compose.runtime.Immutable


@Immutable
data class MainViewUiState(
    val isGlobalLoadingVisible: Boolean = false,
    val isPartialLoadingVisible: Boolean = false,
    val partialLoadingMessages: String? = null,
    val basketCount: Int = 0
)


@Immutable
sealed class MainSideEffect {

}

@Immutable
sealed class MainUiActions {
    data object LoadData : MainUiActions()
}