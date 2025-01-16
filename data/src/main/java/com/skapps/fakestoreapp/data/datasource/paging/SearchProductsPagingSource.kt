package com.skapps.fakestoreapp.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skapps.fakestoreapp.data.datasource.paging.ProductsPagingSource.Companion
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSource
import com.skapps.fakestoreapp.data.di.Dispatcher
import com.skapps.fakestoreapp.data.di.DispatcherType
import com.skapps.fakestoreapp.data.mapper.toEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SortType
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SearchProductsPagingSource @AssistedInject constructor(
    private val productsRemoteSource: ProductsRemoteSource,
    @Assisted private val query: String,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher,
) : PagingSource<Int, ProductEntity>() {
    companion object {
        private const val INITIAL_PAGE = 1
        private const val ERROR_MESSAGE = "An error occurred while loading search products"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            withContext(dispatcher) {
                val currentPage = params.key ?: INITIAL_PAGE
                val response = fetchProducts(
                    currentPage = currentPage,
                    loadSize = params.loadSize,
                    query = query
                )
                when {
                    !response.isSuccessful -> createErrorResult(ERROR_MESSAGE)
                    else -> {
                        val products =
                            response.body()?.products?.map { it.toEntity() } ?: emptyList()
                        createSuccessResult(products, currentPage)
                    }
                }
            }
        } catch (e: Exception) {
            createErrorResult(e.message ?: ERROR_MESSAGE, e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


    private suspend fun fetchProducts(
        currentPage: Int,
        loadSize: Int,
        query: String
    ) = productsRemoteSource.search(
        query = query,
        skip = currentPage,
        limit = loadSize
    )

    private fun createSuccessResult(
        products: List<ProductEntity>,
        currentPage: Int
    ): LoadResult<Int, ProductEntity> = LoadResult.Page(
        data = products,
        prevKey = if (currentPage == INITIAL_PAGE) null else currentPage - 1,
        nextKey = if (products.isEmpty()) null else currentPage + 1
    )


    private fun createErrorResult(
        message: String,
        exception: Exception? = null
    ): LoadResult<Int, ProductEntity> = LoadResult.Error(
        exception ?: Exception(message)
    )


}