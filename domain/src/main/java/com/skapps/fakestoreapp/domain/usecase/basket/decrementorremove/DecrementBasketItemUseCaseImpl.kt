package com.skapps.fakestoreapp.domain.usecase.basket.decrementorremove

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DecrementBasketItemUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : DecrementBasketItemUseCase {
    override suspend fun invoke(id: Int): IResult<Unit, ApiErrorModel> {
        // Simulate network delay
        delay(1000)
        return repository.decrementQuantityOrRemove(id)
    }
}