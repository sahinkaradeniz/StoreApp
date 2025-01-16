package com.skapps.fakestoreapp.data.datasource.local.basket

import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.models.cart.BasketProductsDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.UiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BasketLocalDataSourceImpl @Inject constructor(
    private val basketDao: BasketDao,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher
) : BasketLocalDataSource {

    override suspend fun upsertProduct(product: BasketProductsDbModel): IResult<BasketProductsDbModel, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                basketDao.upsertProduct(product)
                IResult.Success(product)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while upserting product to basket"
                    )
                )
            }
        }

    override suspend fun deleteProductById(id: Int): IResult<Int, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                basketDao.deleteProductById(id)
                IResult.Success(id)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while deleting product from basket"
                    )
                )
            }
        }

    override suspend fun clearBasket(): IResult<Unit, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                basketDao.clearBasket()
                IResult.Success(Unit)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while clearing the basket"
                    )
                )
            }
        }

    override suspend fun getAllProductsOnce(): IResult<List<BasketProductsDbModel>, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                val products = basketDao.getAllProductsOnce()
                IResult.Success(products)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while getting all products from basket"
                    )
                )
            }
        }

    override suspend fun getProductById(id: Int): IResult<BasketProductsDbModel?, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                val product = basketDao.getProductById(id)
                IResult.Success(product)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while getting product by ID from basket"
                    )
                )
            }
        }

    override suspend fun incrementQuantity(id: Int): IResult<Unit, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                basketDao.incrementQuantity(id)
                IResult.Success(Unit)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while incrementing product quantity"
                    )
                )
            }
        }

    override suspend fun decrementQuantityOrRemove(id: Int): IResult<Unit, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                basketDao.decrementQuantityOrRemove(id)
                IResult.Success(Unit)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while decrementing product quantity"
                    )
                )
            }
        }

    override suspend fun addOrIncrementProduct(product: BasketProductsDbModel): IResult<Unit, ApiErrorModel> {
        return try {
            basketDao.addOrIncrementProduct(product)
            IResult.Success(Unit)
        } catch (e: Exception) {
            IResult.Error(UiError.IO(e.message ?: "Transaction error"))
        }
    }

    override fun getTotalQuantityFlow(): Flow<Int?> {
        return basketDao.getTotalQuantityFlow()
    }

    override fun getAllProductsFlow(): Flow<List<BasketProductsDbModel>> {
        return basketDao.getAllProductsFlow()
    }
}