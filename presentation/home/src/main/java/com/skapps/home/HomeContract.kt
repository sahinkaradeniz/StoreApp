package com.skapps.home

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow


data class HomeUiState(
    val products: Flow<PagingData<ProductEntity>>,
    val searchResults: Flow<PagingData<ProductEntity>>,
    val query: String = "",
    val isSearchMode: Boolean = false,
    val isSortSheetVisible: Boolean = false,
    val selectedSortOption: SortType = SortType.NONE
)


sealed interface HomeUiAction {
    data object LoadPagedProducts : HomeUiAction
    data class ProductClicked(val id: String) : HomeUiAction
    data class SearchQueryChanged(val query: String) : HomeUiAction
    data object ClearSearch : HomeUiAction
    data object SortClicked : HomeUiAction
    data class SortOptionSelected(val sortOption: SortType) : HomeUiAction
}

sealed class HomeSideEffect {
    data class ShowError(val message: String) : HomeSideEffect()
    data class NavigateToProductDetail(val id: String) : HomeSideEffect()
}
