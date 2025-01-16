package com.skapps.fakestoreapp.domain.usecase.favorites.getAll

import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import kotlinx.coroutines.flow.Flow


interface GetAllFavoriteProductsUseCase {
    suspend operator fun invoke(): Flow<List<FavoritesEntity>>
}