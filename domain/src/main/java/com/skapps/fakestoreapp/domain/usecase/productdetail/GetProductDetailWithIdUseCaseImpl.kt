package com.skapps.fakestoreapp.domain.usecase.productdetail

import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import javax.inject.Inject

class GetProductDetailWithIdUseCaseImpl @Inject constructor(
    private val repository: ProductsRepository
) : GetProductDetailWithIdUseCase {
    override suspend fun invoke(id: String): IResult<ProductEntity, ApiErrorModel> =
        repository.getProductById(id)

}