package com.skapps.fakestoreapp.domain.repository

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    suspend fun upsertProduct(product: BasketProductEntity): IResult<BasketProductEntity, ApiErrorModel>

    suspend fun deleteProductById(id: Int): IResult<Int, ApiErrorModel>

    suspend fun clearBasket(): IResult<Unit, ApiErrorModel>

    suspend fun getAllProductsOnce(): IResult<List<BasketProductEntity>, ApiErrorModel>

    suspend fun getProductById(id: Int): IResult<BasketProductEntity?, ApiErrorModel>

    suspend fun incrementQuantity(id: Int): IResult<Unit, ApiErrorModel>

    suspend fun decrementQuantityOrRemove(id: Int): IResult<Unit, ApiErrorModel>

    fun getTotalQuantityFlow(): Flow<Int?>

    fun getAllProductsFlow(): Flow<List<BasketProductEntity>>

    suspend fun addOrIncrementProduct(product: BasketProductEntity): IResult<Unit, ApiErrorModel>
}