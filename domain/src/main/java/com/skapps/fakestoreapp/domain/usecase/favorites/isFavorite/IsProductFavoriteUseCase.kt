package com.skapps.fakestoreapp.domain.usecase.favorites.isFavorite

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult

interface IsProductFavoriteUseCase {
    suspend operator fun invoke(id:Int): IResult<Boolean, ApiErrorModel>
}