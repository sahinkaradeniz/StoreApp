package com.skapps.fakestoreapp.domain.usecase.searchpagedproducts

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SearchPagedProductParams
import kotlinx.coroutines.flow.Flow

interface SearchPagedProductsUseCase {
    suspend operator fun invoke(params: SearchPagedProductParams): Flow<PagingData<ProductEntity>>
}