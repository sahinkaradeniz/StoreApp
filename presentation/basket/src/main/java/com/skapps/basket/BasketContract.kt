package com.skapps.basket

data class BasketUiState(
    val items: List<BasketItemUiModel> = emptyList(),
    val totalPrice: Double = 0.0,
    val totalDiscount: Double = 0.0,
    val total: Double = 0.0,
    val isEmpty: Boolean = true
) {
    companion object {
        val EMPTY = BasketUiState()
    }
}

sealed class BasketUiAction {
    data object LoadBasket : BasketUiAction()
    data class IncreaseQuantity(val itemId: Int) : BasketUiAction()
    data class DecreaseQuantity(val itemId: Int) : BasketUiAction()
    data class RemoveItem(val itemId: Int, val loadingMessage: String) : BasketUiAction()
    data object Checkout : BasketUiAction()
}

sealed class BasketSideEffect {
    data class ShowError(val message: String) : BasketSideEffect()
    data object NavigateCheckoutScreen : BasketSideEffect()
}

data class BasketItemUiModel(
    val id: Int,
    val title: String,
    val image: String,
    val price: Double,
    val oldPrice: Double,
    val quantity: Int
)