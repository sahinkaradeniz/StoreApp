package com.skapps.fakestoreapp.domain.usecase.favorites.isFavorite

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import javax.inject.Inject


class IsProductFavoriteUseCaseImpl @Inject constructor(
    private val favoritesRepository: FavoriteProductsRepository
) : IsProductFavoriteUseCase {
    override suspend operator fun invoke(id: Int): IResult<Boolean, ApiErrorModel> {
        return favoritesRepository.isProductFavorite(id = id)
    }
}