package com.skapps.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.coreui.theme.logError
import com.skapps.fakestoreapp.domain.entitiy.GetPagedProductsParams
import com.skapps.fakestoreapp.domain.entitiy.SearchPagedProductParams
import com.skapps.fakestoreapp.domain.entitiy.SortType
import com.skapps.fakestoreapp.domain.usecase.getpagedproducts.GetPagedProductsUseCase
import com.skapps.fakestoreapp.domain.usecase.searchpagedproducts.SearchPagedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getPagedProductsUseCase: GetPagedProductsUseCase,
    private val searchPagedProductsUseCase: SearchPagedProductsUseCase
) : BaseViewModel<HomeUiState, HomeUiAction, HomeSideEffect>(
    initialState = HomeUiState(products = emptyFlow(), searchResults = emptyFlow()),
    globalLoadingManager = loadingManager
) {
    init {
        onAction(HomeUiAction.LoadPagedProducts)
    }

    override fun onAction(uiAction: HomeUiAction) {
        when (uiAction) {
            is HomeUiAction.LoadPagedProducts -> {
                loadPagedProducts()
            }

            is HomeUiAction.ProductClicked -> {
                viewModelScope.emitSideEffect(HomeSideEffect.NavigateToProductDetail(uiAction.id))
            }

            is HomeUiAction.ClearSearch -> {
                updateUiState { copy(query = "", isSearchMode = false) }
            }

            is HomeUiAction.SearchQueryChanged -> {
                updateUiState { copy(query = uiAction.query) }
                if (uiAction.query.isEmpty()) {
                    onAction(HomeUiAction.ClearSearch)
                } else if (uiAction.query.length == 1) {
                    updateUiState { copy(isSearchMode = true) }
                } else if (uiAction.query.length > 1) {
                    loadSearchResults(uiAction.query)
                }
            }

            is HomeUiAction.SortClicked -> {
                updateUiState { copy(isSortSheetVisible = true) }
            }

            is HomeUiAction.SortOptionSelected -> {
                updateUiState {
                    copy(
                        selectedSortOption = uiAction.sortOption,
                        isSortSheetVisible = false
                    )
                }
                onAction(HomeUiAction.LoadPagedProducts)
            }
        }
    }

    private fun loadPagedProducts() {
        viewModelScope.launch {
            val flow = getPagedProductsUseCase(
                params = GetPagedProductsParams(
                    sortType = uiState.value.selectedSortOption
                )
            ).distinctUntilChanged()
                .cachedIn(viewModelScope)

            updateUiState { copy(products = flow) }
        }
    }

    private fun loadSearchResults(query: String) {
        viewModelScope.launch {
            val flow = searchPagedProductsUseCase(
                SearchPagedProductParams(
                    query = query
                )
            ).distinctUntilChanged()
                .cachedIn(viewModelScope)
            updateUiState { copy(searchResults = flow, isSearchMode = true) }
        }
    }

    fun onSortClicked() = onAction(HomeUiAction.SortClicked)

    fun onSortOptionSelected(sortOption: SortType) =
        onAction(HomeUiAction.SortOptionSelected(sortOption))

    fun onProductClicked(id: String) {
        onAction(HomeUiAction.ProductClicked(id))
    }

    fun onSearchQueryChanged(query: String) {
        onAction(HomeUiAction.SearchQueryChanged(query))
    }

    fun onClearSearch() {
        onAction(HomeUiAction.ClearSearch)
    }


}