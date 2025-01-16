package com.skapps.basket

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.onErrorWithMessage
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove.DecrementBasketItemUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.delete.DeleteBasketProductByIdUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.getallproductsflow.GetAllBasketProductsFlowUseCase
import com.skapps.fakestoreapp.domain.usecase.basket.incrementquantity.IncrementBasketItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    loadingManager: GlobalLoadingManager,
    private val getBasketItemsUseCase: GetAllBasketProductsFlowUseCase,
    private val decrementBasketItemUseCase: DecrementBasketItemUseCase,
    private val incrementProductUseCase: IncrementBasketItemUseCase,
    private val deleteProductFromBasketUseCase: DeleteBasketProductByIdUseCase
) : BaseViewModel<BasketUiState, BasketUiAction, BasketSideEffect>(
    BasketUiState.EMPTY, loadingManager
) {
    override fun onAction(uiAction: BasketUiAction) {
        when (uiAction) {
            is BasketUiAction.LoadBasket -> loadBasket()
            is BasketUiAction.IncreaseQuantity -> incrementItem(uiAction.itemId)
            is BasketUiAction.DecreaseQuantity -> decrementItem(uiAction.itemId)
            is BasketUiAction.RemoveItem -> removeItem(uiAction.itemId, uiAction.loadingMessage)
            is BasketUiAction.Checkout -> viewModelScope.launch { emitSideEffect(BasketSideEffect.NavigateCheckoutScreen) }
        }
    }

    private fun decrementItem(itemId: Int) {
        viewModelScope.launch {
            doWithLocalLoading({
                decrementBasketItemUseCase(itemId).onSuccess { loadBasket() }.onErrorWithMessage {
                    emitSideEffect(BasketSideEffect.ShowError(it))
                }
            }, viewKey =  quantityLoadingKey+"$itemId")
        }
    }

    private fun incrementItem(itemId: Int) {
        viewModelScope.launch {
            doWithLocalLoading({
                incrementProductUseCase(itemId).onSuccess { loadBasket() }.onErrorWithMessage {
                    emitSideEffect(BasketSideEffect.ShowError(it))
                }
            }, viewKey = quantityLoadingKey+"$itemId")
        }
    }

    private fun loadBasket() {
        viewModelScope.launch {
            getBasketItemsUseCase().distinctUntilChanged().catch {
                emitSideEffect(BasketSideEffect.ShowError(it.message ?: "Error"))
            }.collectLatest {
                val uiList = it.map { e ->
                    BasketItemUiModel(e.id, e.title, e.image, e.price, e.oldPrice, e.quantity)
                }
                val totalPrice = uiList.sumOf { i -> i.price * i.quantity }
                val totalOld = uiList.sumOf { i -> i.oldPrice * i.quantity }
                val discount = (totalOld - totalPrice).coerceAtLeast(0.0)
                updateUiState {
                    copy(
                        items = uiList,
                        totalPrice = totalPrice,
                        totalDiscount = discount,
                        total = totalPrice - discount,
                        isEmpty = uiList.isEmpty()
                    )
                }
            }
        }
    }

    private fun removeItem(itemId: Int, message: String) {
        viewModelScope.launch {
            doWithPartialLoading({
                deleteProductFromBasketUseCase(itemId).onSuccess { loadBasket() }.onErrorWithMessage {
                    emitSideEffect(BasketSideEffect.ShowError(it))
                }
            }, message = message)
        }
    }

    companion object {
        const val quantityLoadingKey = "BasketItem"
    }
}