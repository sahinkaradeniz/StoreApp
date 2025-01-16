package com.skapps.fakestoreapp.domain.usecase.basket.clear

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject


class ClearBasketUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : ClearBasketUseCase {
    override suspend fun invoke(): IResult<Unit, ApiErrorModel> {
        return repository.clearBasket()
    }
}