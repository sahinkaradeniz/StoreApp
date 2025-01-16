package com.skapps.fakestoreapp.domain.usecase.basket.incrementquantity

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult

interface IncrementBasketItemUseCase {
    suspend operator fun invoke(id: Int): IResult<Unit, ApiErrorModel>
}
