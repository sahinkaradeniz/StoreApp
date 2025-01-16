package com.skapps.fakestoreapp.domain.repository

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteProductsRepository {
    suspend fun addProductToFavorites(favoritesEntity: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel>
    suspend fun deleteProductFromFavorites(id:String): IResult<String, ApiErrorModel>
    suspend fun getAllFavoriteProducts(): Flow<List<FavoritesEntity>>
    suspend fun getFavoriteProductWithId(product: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel>
    suspend fun isProductFavorite(id: Int): IResult<Boolean, ApiErrorModel>
}