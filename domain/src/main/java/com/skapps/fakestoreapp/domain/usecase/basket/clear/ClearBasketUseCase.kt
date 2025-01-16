package com.skapps.fakestoreapp.domain.usecase.basket.clear

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult

interface ClearBasketUseCase {
    suspend operator fun invoke(): IResult<Unit, ApiErrorModel>
}