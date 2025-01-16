package com.skapps.fakestoreapp.domain.usecase.basket.addorincrement

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class AddOrIncrementProductUseCaseImpl @Inject constructor(
    private val repository: BasketRepository
) : AddOrIncrementProductUseCase{

    override suspend fun invoke(product: BasketProductEntity): IResult<Unit, ApiErrorModel> {
        delay(3000)
        return repository.addOrIncrementProduct(product)
    }
}