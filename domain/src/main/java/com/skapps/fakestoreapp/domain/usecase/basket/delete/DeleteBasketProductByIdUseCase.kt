package com.skapps.fakestoreapp.domain.usecase.basket.delete

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult

interface DeleteBasketProductByIdUseCase {
    suspend operator fun invoke(id: Int): IResult<Int, ApiErrorModel>
}