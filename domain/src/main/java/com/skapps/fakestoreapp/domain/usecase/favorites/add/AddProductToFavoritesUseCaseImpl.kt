package com.skapps.fakestoreapp.domain.usecase.favorites.add

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AddProductToFavoritesUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoriteProductsRepository
): AddProductToFavoritesUseCase {
    override suspend fun invoke(favoritesEntity: FavoritesEntity): IResult<FavoritesEntity, ApiErrorModel> {
        delay(3000)
        return favoritesRepository.addProductToFavorites(favoritesEntity)
    }
}