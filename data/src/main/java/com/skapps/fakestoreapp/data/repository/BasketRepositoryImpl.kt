package com.skapps.fakestoreapp.data.repository

import com.skapps.fakestoreapp.data.datasource.local.basket.BasketLocalDataSource
import com.skapps.fakestoreapp.data.models.cart.toDbModel
import com.skapps.fakestoreapp.data.models.cart.toEntity
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketLocalDataSource: BasketLocalDataSource
) : BasketRepository {

    override suspend fun upsertProduct(
        product: BasketProductEntity
    ): IResult<BasketProductEntity, ApiErrorModel> {
        return basketLocalDataSource.upsertProduct(product.toDbModel()).mapSuccess {
            it.toEntity()
       }
    }

    override suspend fun deleteProductById(id: Int): IResult<Int, ApiErrorModel> {
        return basketLocalDataSource.deleteProductById(id)
    }

    override suspend fun clearBasket(): IResult<Unit, ApiErrorModel> {
        return basketLocalDataSource.clearBasket()
    }

    override suspend fun getAllProductsOnce(): IResult<List<BasketProductEntity>, ApiErrorModel> {
       return basketLocalDataSource.getAllProductsOnce().mapSuccess {
            it.map { it.toEntity() }
        }
    }

    override suspend fun getProductById(id: Int): IResult<BasketProductEntity?, ApiErrorModel> {
      return basketLocalDataSource.getProductById(id).mapSuccess {
            it?.toEntity()
        }
    }

    override suspend fun incrementQuantity(id: Int): IResult<Unit, ApiErrorModel> {
        return basketLocalDataSource.incrementQuantity(id)
    }

    override suspend fun decrementQuantityOrRemove(id: Int): IResult<Unit, ApiErrorModel> {
        return basketLocalDataSource.decrementQuantityOrRemove(id)
    }

    override fun getTotalQuantityFlow(): Flow<Int?> {
        return basketLocalDataSource.getTotalQuantityFlow()
    }

    override fun getAllProductsFlow(): Flow<List<BasketProductEntity>> {
        return basketLocalDataSource.getAllProductsFlow()
            .map { dbModelList ->
                dbModelList.map { dbModel ->
                    dbModel.toEntity()
                }
            }
    }

    override suspend fun addOrIncrementProduct(product: BasketProductEntity): IResult<Unit, ApiErrorModel> {
        return basketLocalDataSource.addOrIncrementProduct(product.toDbModel())
    }
}