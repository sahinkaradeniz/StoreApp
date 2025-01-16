package com.skapps.fakestoreapp.domain.usecase.favorites.getAll

import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteProductsUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoriteProductsRepository
) : GetAllFavoriteProductsUseCase {
    override suspend operator fun invoke(): Flow<List<FavoritesEntity>> {
        return favoritesRepository.getAllFavoriteProducts()
    }
}