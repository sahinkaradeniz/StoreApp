package com.skapps.fakestoreapp.domain.usecase.basket.getallonce

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject

class GetAllProductsOnceUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : GetAllProductsOnceUseCase {
    override suspend fun invoke(): IResult<List<BasketProductEntity>, ApiErrorModel> {
        return repository.getAllProductsOnce()
    }
}