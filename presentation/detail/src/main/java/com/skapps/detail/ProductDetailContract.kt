package com.skapps.detail

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.UiError

data class ProductDetailUiState(
    val product: ProductDetailUiModel,
    val isFavorite: Boolean = false
) {
    companion object {
        val EMPTY by lazy { ProductDetailUiState(ProductDetailUiModel()) }
    }
}

sealed interface ProductDetailUiAction {
    data class LoadProduct(val productId: String) : ProductDetailUiAction
    data class FavoriteClicked(val loadingMessage: String) : ProductDetailUiAction
    data class AddToCartClicked(val loadingMessage: String) : ProductDetailUiAction
}


sealed class ProductDetailSideEffect {
    data class ShowError(val error: UiError<ApiErrorModel>) : ProductDetailSideEffect()
    data class ShowErrorGetProduct(val error: UiError<ApiErrorModel>) : ProductDetailSideEffect()
}

data class ProductDetailUiModel(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val price: Double = 0.0,
    val oldPrice: Double = 0.0,
    val discountPercentage: Double? = null,
    val rating: Double = 0.0
)