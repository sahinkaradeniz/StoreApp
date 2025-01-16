package com.skapps.fakestoreapp.data.repository

import com.skapps.fakestoreapp.data.datasource.local.favorites.FavoritesLocalDataSource
import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.mapper.toDbModel
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteProductsRepositoryImpl @Inject constructor(
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher
) : FavoriteProductsRepository {
    override suspend fun addProductToFavorites(favoritesEntity: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.addProductToFavorites(favoritesEntity.toDbModel())
                .mapSuccess { it.toEntity() }
        }

    override suspend fun deleteProductFromFavorites(id: String): IResult<String, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.deleteProductToFavorites(id = id)
                .mapSuccess { it }
        }


    override suspend fun getAllFavoriteProducts(): Flow<List<FavoritesEntity>> =
        withContext(dispatcher) {
            favoritesLocalDataSource.getAllProducts().map { it.map { it.toEntity() } }

        }


    override suspend fun getFavoriteProductWithId(product: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.getFavoriteProductWithId(product.id)
                .mapSuccess { it.toEntity() }
        }

    override suspend fun isProductFavorite(id: Int): IResult<Boolean, ApiErrorModel> =
        withContext(dispatcher) {
            favoritesLocalDataSource.isProductFavorite(id)
        }
}