package com.skapps.fakestoreapp.domain.usecase.favorites.delete

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity

interface DeleteProductToFavoritesUseCase {
    suspend operator fun invoke(id:String): IResult<String, ApiErrorModel>
}