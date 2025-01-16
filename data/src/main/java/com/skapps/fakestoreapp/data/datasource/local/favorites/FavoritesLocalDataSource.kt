package com.skapps.fakestoreapp.data.datasource.local.favorites

import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {

    suspend fun addProductToFavorites(favoritesDbModel: FavoritesDbModel): IResult<FavoritesDbModel, ApiErrorModel>

    suspend fun deleteProductToFavorites(id:String): IResult<String, ApiErrorModel>

    suspend fun getAllProducts(): Flow<List<FavoritesDbModel>>

    suspend fun getFavoriteProductWithId(id: Int): IResult<FavoritesDbModel, ApiErrorModel>

    suspend fun isProductFavorite(id: Int): IResult<Boolean, ApiErrorModel>
}