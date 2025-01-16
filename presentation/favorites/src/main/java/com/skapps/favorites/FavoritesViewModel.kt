package com.skapps.favorites

import androidx.lifecycle.viewModelScope
import com.skapps.fakestoreapp.core.base.BaseViewModel
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.onError
import com.skapps.fakestoreapp.domain.onErrorWithMessage
import com.skapps.fakestoreapp.domain.onSuccess
import com.skapps.fakestoreapp.domain.usecase.basket.addorincrement.AddOrIncrementProductUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.delete.DeleteProductToFavoritesUseCase
import com.skapps.fakestoreapp.domain.usecase.favorites.getAll.GetAllFavoriteProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    globalLoadingManager: GlobalLoadingManager,
    private val getAllFavoriteProductsUseCase: GetAllFavoriteProductsUseCase,
    private val deleteProductToFavoritesUseCase: DeleteProductToFavoritesUseCase,
    private val addOrIncrementProductUseCase: AddOrIncrementProductUseCase
) : BaseViewModel<FavoritesUiState, FavoritesUiAction, FavoritesSideEffect>(
    initialState = FavoritesUiState.EMPTY,
    globalLoadingManager = globalLoadingManager
) {

    init {
        onAction(FavoritesUiAction.LoadFavorites)
    }


    override fun onAction(uiAction: FavoritesUiAction) {
        when (uiAction) {
            is FavoritesUiAction.FavoriteButtonClicked -> {
                deleteProductToFavorites(uiAction.id, uiAction.loadingMessage)
            }

            is FavoritesUiAction.LoadFavorites -> {
                getAllFavoriteProducts()
            }

            is FavoritesUiAction.ProductClicked -> {
                navigateToProductDetail(uiAction.id)
            }

            is FavoritesUiAction.AddToCartClicked -> {
                addProductToBasket(
                    product = uiAction.item,
                    message = uiAction.loadingMessage
                )
            }
        }
    }

    private fun navigateToProductDetail(id: String) {
        viewModelScope.launch {
            emitSideEffect(FavoritesSideEffect.NavigateToProductDetail(id))
        }
    }


    private fun getAllFavoriteProducts() {
        viewModelScope.launch {
            getAllFavoriteProductsUseCase.invoke().distinctUntilChanged().catch {
                emitSideEffect(FavoritesSideEffect.ShowErrorGetFavorites(it.message ?: "Error"))
            }.collectLatest {
                updateUiState {
                    copy(
                        favorites = it.map { it.toFavoriteUiModel() },
                        isEmpty = it.isEmpty()
                    )
                }
            }
        }
    }

    private fun deleteProductToFavorites(id: String, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(block = {
                deleteProductToFavoritesUseCase.invoke(id).onErrorWithMessage {
                    emitSideEffect(FavoritesSideEffect.ShowErrorDeleteFavorites(it))
                }.onSuccess {
                    emitSideEffect(FavoritesSideEffect.ShowSuccessDeleteFavorites)
                }
            }, message = message)
        }
    }

    private fun addProductToBasket(product: FavoriteUiModel, message: String) {
        viewModelScope.launch {
            doWithPartialLoading(
                block = {
                    addOrIncrementProductUseCase(product.toBasketModel())
                        .onErrorWithMessage {
                            emitSideEffect(FavoritesSideEffect.ShowErrorAddToCart(it))
                        }.onSuccess {
                            emitSideEffect(FavoritesSideEffect.ShowSuccessAddToCart)
                        }
                }, message = message
            )
        }
    }

    private fun FavoritesEntity.toFavoriteUiModel(): FavoriteUiModel {
        return FavoriteUiModel(
            id = id,
            title = title,
            description = description,
            thumbnail = images,
            price = newPrice,
            oldPrice = oldPrice,
            isFavorite = true
        )
    }

}