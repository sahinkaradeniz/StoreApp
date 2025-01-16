package com.skapps.fakestoreapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSource
import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.di.PagingModule
import com.skapps.fakestoreapp.data.mapper.parseError
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiExecutor
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiResult
import com.skapps.fakestoreapp.domain.ApiErrorModel
import com.skapps.fakestoreapp.domain.IResult
import com.skapps.fakestoreapp.domain.entitiy.GetPagedProductsParams
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SearchPagedProductParams
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteSource: ProductsRemoteSource,
    private val productsPagingSource: PagingModule.ProductsPagingSourceFactory,
    private val searchProductsPagingSource: PagingModule.SearchProductsPagingSourceFactory,
    private val apiExecutor: ApiExecutor,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher,
) : ProductsRepository {
    override suspend fun getProductsPagingSource(params: GetPagedProductsParams): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                productsPagingSource.create(params.sortType)
            }
        ).flow
    }

    override suspend fun searchProducts(
        params: SearchPagedProductParams
    ): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = params.pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                searchProductsPagingSource.create(params.query)
            }
        ).flow
    }

    override suspend fun getProductById(id: String): IResult<ProductEntity, ApiErrorModel> {
        return withContext(dispatcher) {
            when (val result = apiExecutor.execute {
                remoteSource.getProductById(id)
            }) {
                is ApiResult.Success -> IResult.Success(result.response.toEntity())
                is ApiResult.Error -> parseError<ApiErrorModel>(result)
            }
        }
    }


}