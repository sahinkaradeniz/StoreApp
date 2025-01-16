package com.skapps.fakestoreapp.domain.usecase.basket.delete

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class DeleteBasketProductByIdUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : DeleteBasketProductByIdUseCase {
    override suspend fun invoke(id: Int): IResult<Int, ApiErrorModel> {
        // Simulate network delay
        delay(1000)
        return repository.deleteProductById(id)
    }
}