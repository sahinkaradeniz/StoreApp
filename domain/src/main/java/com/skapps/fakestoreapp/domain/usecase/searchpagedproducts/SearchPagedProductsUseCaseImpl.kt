package com.skapps.fakestoreapp.domain.usecase.searchpagedproducts

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SearchPagedProductParams
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPagedProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : SearchPagedProductsUseCase {
    override suspend fun invoke(params: SearchPagedProductParams): Flow<PagingData<ProductEntity>> {
        return productsRepository.searchProducts(params)
    }
}