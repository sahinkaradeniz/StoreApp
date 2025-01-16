package com.skapps.fakestoreapp.domain.usecase.basket.upsert

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity

interface UpsertProductUseCase {
    suspend operator fun invoke(product: BasketProductEntity): IResult<BasketProductEntity, ApiErrorModel>
}
