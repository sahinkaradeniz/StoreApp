package com.skapps.fakestoreapp.data.datasource.local.basket
import com.skapps.fakestoreapp.data.models.cart.BasketProductsDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import kotlinx.coroutines.flow.Flow

interface BasketLocalDataSource {

    suspend fun upsertProduct(product: BasketProductsDbModel): IResult<BasketProductsDbModel, ApiErrorModel>

    suspend fun deleteProductById(id: Int): IResult<Int, ApiErrorModel>

    suspend fun clearBasket(): IResult<Unit, ApiErrorModel>

    suspend fun getAllProductsOnce(): IResult<List<BasketProductsDbModel>, ApiErrorModel>

    suspend fun getProductById(id: Int): IResult<BasketProductsDbModel?, ApiErrorModel>

    suspend fun incrementQuantity(id: Int): IResult<Unit, ApiErrorModel>

    suspend fun decrementQuantityOrRemove(id: Int): IResult<Unit, ApiErrorModel>

    fun getTotalQuantityFlow(): Flow<Int?>

    fun getAllProductsFlow(): Flow<List<BasketProductsDbModel>>

    suspend fun addOrIncrementProduct(product: BasketProductsDbModel): IResult<Unit, ApiErrorModel>
}