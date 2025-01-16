package com.skapps.fakestoreapp.domain.usecase.getpagedproducts

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.entitiy.GetPagedProductsParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import kotlinx.coroutines.flow.Flow

interface GetPagedProductsUseCase  {
    suspend operator fun invoke(params: GetPagedProductsParams): Flow<PagingData<ProductEntity>>
}