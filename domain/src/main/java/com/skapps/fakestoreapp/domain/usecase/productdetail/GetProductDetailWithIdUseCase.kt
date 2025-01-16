package com.skapps.fakestoreapp.domain.usecase.productdetail

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity

interface GetProductDetailWithIdUseCase {
   suspend operator fun invoke(id: String): IResult<ProductEntity, ApiErrorModel>
}