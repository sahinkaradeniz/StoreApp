package com.skapps.fakestoreapp.domain.usecase.basket.getallonce

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity

interface GetAllProductsOnceUseCase {
    suspend operator fun invoke(): IResult<List<BasketProductEntity>, ApiErrorModel>
}