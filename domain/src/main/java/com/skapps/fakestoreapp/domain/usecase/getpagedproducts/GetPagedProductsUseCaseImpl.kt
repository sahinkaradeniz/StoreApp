package com.skapps.fakestoreapp.domain.usecase.getpagedproducts

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.GetPagedProductsParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
): GetPagedProductsUseCase {
    override suspend fun invoke(params: GetPagedProductsParams): Flow<PagingData<ProductEntity>> {
        return productsRepository.getProductsPagingSource(params)
    }
}