package com.skapps.fakestoreapp.domain.usecase.basket.addorincrement

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity

interface AddOrIncrementProductUseCase {
    suspend operator fun invoke(product: BasketProductEntity): IResult<Unit, ApiErrorModel>
}