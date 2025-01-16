package com.skapps.fakestoreapp.data.datasource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
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

class ProductsPagingSource @AssistedInject constructor(
    @Assisted private val sortType: SortType,
    @Dispatcher(DispatcherType.Io) private val dispatcher: CoroutineDispatcher,
    private val productsRemoteSource: ProductsRemoteSource,
) : PagingSource<Int, ProductEntity>() {

    companion object {
        private const val INITIAL_PAGE = 1
        private const val ERROR_MESSAGE = "An error occurred while loading products"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductEntity> {
        return try {
            withContext(dispatcher) {
                val currentPage = params.key ?: INITIAL_PAGE
                val sortingParams = getSortingParameters(sortType)

                val response = fetchProducts(
                    currentPage = currentPage,
                    loadSize = params.loadSize,
                    sortParams = sortingParams
                )

                when {
                    !response.isSuccessful -> createErrorResult(ERROR_MESSAGE)
                    else -> {
                        val products = response.body()?.products?.map { it.toEntity() } ?: emptyList()
                        createSuccessResult(products, currentPage)
                    }
                }
            }
        } catch (e: Exception) {
            createErrorResult(e.message ?: ERROR_MESSAGE, e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return calculateRefreshKey(state)
    }

    private fun getSortingParameters(sortType: SortType): Pair<String?, String?> {
        return when (sortType) {
            SortType.NONE -> null to null
            SortType.PRICE_ASC -> "price" to "asc"
            SortType.PRICE_DESC -> "price" to "desc"
            SortType.TITLE_ASC -> "title" to "asc"
            SortType.TITLE_DESC -> "title" to "desc"
        }
    }

    private suspend fun fetchProducts(
        currentPage: Int,
        loadSize: Int,
        sortParams: Pair<String?, String?>
    ) = productsRemoteSource.getAllProducts(
        skip = currentPage,
        limit = loadSize,
        sortBy = sortParams.first,
        order = sortParams.second
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


    private fun calculateRefreshKey(state: PagingState<Int, ProductEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}