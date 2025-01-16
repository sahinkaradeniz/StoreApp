package com.skapps.fakestoreapp.data.datasource.local.favorites

import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.UiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoritesLocalDataSourceImpl @Inject constructor(
    private val favoritesDao: FavoritesDao,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher
) : FavoritesLocalDataSource {
    override suspend fun addProductToFavorites(favoritesDbModel: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                favoritesDao.addProductToFavorites(favoritesDbModel)
                IResult.Success(favoritesDbModel)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while adding product to favorites"
                    )
                )
            }
        }

    override suspend fun deleteProductToFavorites(id:String): IResult<String, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                favoritesDao.deleteProductToFavorites(id = id)
                IResult.Success(id)
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while deleting product from favorites"
                    )
                )
            }
        }

    override suspend fun getAllProducts(): Flow<List<FavoritesDbModel>> =
        withContext(dispatcher) {
            favoritesDao.getAllProducts()
        }

    override suspend fun getFavoriteProductWithId(id: Int): IResult<FavoritesDbModel, ApiErrorModel> =
        withContext(dispatcher) {
            try {
                IResult.Success(favoritesDao.getFavoriteProductWithId(id))
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while getting product from favorites"
                    )
                )
            }
        }

    override suspend fun isProductFavorite(id: Int): IResult<Boolean, ApiErrorModel> {
        return withContext(dispatcher) {
            try {
                IResult.Success(favoritesDao.isProductFavorite(id))
            } catch (e: Exception) {
                IResult.Error(
                    UiError.IO(
                        e.message ?: "An error occurred while checking if product is favorite"
                    )
                )
            }
        }
    }
}
