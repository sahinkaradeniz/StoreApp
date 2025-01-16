package com.skapps.fakestoreapp

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.core.loading.LoadingType
import com.skapps.fakestoreapp.domain.usecase.basket.quantitiyflow.GetTotalBasketQuantityFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val globalLoadingManager: GlobalLoadingManager,
    private val getTotalBasketQuantityFlowUseCase: GetTotalBasketQuantityFlowUseCase
) : BaseViewModel<MainViewUiState, MainUiActions, MainSideEffect>(
    initialState = MainViewUiState(),
    globalLoadingManager = globalLoadingManager
) {
    init {
        observeLoadingStates()
        onAction(MainUiActions.LoadData)
    }

    private fun observeLoadingStates() {
        viewModelScope.launch {
            globalLoadingManager.activeLoadings.collect { activeLoadings ->
                val globalVisible = activeLoadings.contains(LoadingType.Global)
                val partialMessages = activeLoadings
                    .filterIsInstance<LoadingType.Partial>()
                    .map { it.message }
                    .firstOrNull()
                val partialVisible = !partialMessages.isNullOrEmpty()
                updateUiState {
                    copy(
                        isGlobalLoadingVisible = globalVisible,
                        partialLoadingMessages = partialMessages,
                        isPartialLoadingVisible = partialVisible
                    )
                }
            }
        }
    }



    override fun onAction(uiAction: MainUiActions) {
       when (uiAction) {
           MainUiActions.LoadData -> {
               loadBasketCount()
           }
       }
    }

    private fun loadBasketCount() {
        viewModelScope.launch {
            getTotalBasketQuantityFlowUseCase().distinctUntilChanged().collectLatest {
                updateUiState {
                    copy(
                        basketCount = it ?: 0
                    )
                }
            }
        }
    }
}
