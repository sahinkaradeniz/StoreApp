package com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult

interface DecrementBasketItemUseCase {
    suspend operator fun invoke(id: Int): IResult<Unit, ApiErrorModel>
}

