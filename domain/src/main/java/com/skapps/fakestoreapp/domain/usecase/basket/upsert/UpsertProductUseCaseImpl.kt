package com.skapps.fakestoreapp.domain.usecase.basket.upsert

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import javax.inject.Inject

class UpsertProductUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : UpsertProductUseCase {
    override suspend fun invoke(product: BasketProductEntity): IResult<BasketProductEntity, ApiErrorModel> {
        return repository.upsertProduct(product)
    }
}