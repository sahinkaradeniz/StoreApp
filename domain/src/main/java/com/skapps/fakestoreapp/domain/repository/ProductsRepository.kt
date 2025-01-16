package com.skapps.fakestoreapp.domain.repository

import androidx.paging.PagingData
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetPagedProductsParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SearchPagedProductParams
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {

    suspend fun getProductsPagingSource(
        params: GetPagedProductsParams
    ): Flow<PagingData<ProductEntity>>

    suspend fun searchProducts(
        params: SearchPagedProductParams
    ): Flow<PagingData<ProductEntity>>

    suspend fun getProductById(id: String): IResult<ProductEntity,ApiErrorModel>

}