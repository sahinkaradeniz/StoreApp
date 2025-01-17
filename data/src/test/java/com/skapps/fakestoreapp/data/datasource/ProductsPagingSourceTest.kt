package com.skapps.fakestoreapp.data.datasource


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skapps.fakestoreapp.data.datasource.paging.ProductsPagingSource
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSource
import com.skapps.fakestoreapp.data.models.ProductDto
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.SortType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response



class ProductsPagingSourceTest {

    private val mockRemoteSource = mockk<ProductsRemoteSource>()

    private lateinit var pagingSource: ProductsPagingSource

    @Before
    fun setup() {
        pagingSource = ProductsPagingSource(
            sortType = SortType.PRICE_ASC,
            productsRemoteSource = mockRemoteSource,
            dispatcher = Dispatchers.Unconfined
        )
    }

    private fun createTestProducts() = listOf(
        ProductDto(
            id = 1,
            title = "Expensive Product",
            price = 100.0,
            description = "Test description A",
            category = "Test category",
            brand = "Test brand",
            thumbnail = "test.jpg",
            images = listOf("test.jpg"),
            rating = 4.5,
            stock = 10,
            discountPercentage = null
        ),
        ProductDto(
            id = 2,
            title = "Cheap Product",
            price = 50.0,
            description = "Test description B",
            category = "Test category",
            brand = "Test brand",
            thumbnail = "test.jpg",
            images = listOf("test.jpg"),
            rating = 4.0,
            stock = 20,
            discountPercentage = null
        )
    )


    @Test
    fun `load should return sorted products when response is successful`() = runTest {
        // ARRANGE
        val testProducts = createTestProducts()
        val responseDto = ProductsResponseDto(
            products = testProducts,
            total = testProducts.size,
            skip = 0,
            limit = 10
        )

        coEvery {
            mockRemoteSource.getAllProducts(
                skip = any(),
                limit = any(),
                sortBy = any(),
                order = any()
            )
        } returns Response.success(responseDto)

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )

        val result = pagingSource.load(params)

        assertTrue("Result must be in Page type", result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page


        assertNull("The prevKey of the first page must be null", result.prevKey)
        assertEquals("Next page 2 should be", 2, result.nextKey)

        coVerify {
            mockRemoteSource.getAllProducts(
                skip = 1,
                limit = 20,
                sortBy = "price",
                order = "asc"
            )
        }
    }


    @Test
    fun `load should handle empty response correctly`() = runTest {
        val emptyResponse = ProductsResponseDto(
            products = emptyList(),
            total = 0,
            skip = 0,
            limit = 10
        )

        coEvery {
            mockRemoteSource.getAllProducts(any(), any(), any(), any())
        } returns Response.success(emptyResponse)

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )

        val result = pagingSource.load(params)

        assertTrue("Result must be in Page type", result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page

        assertTrue("Data list must be empty", result.data.isEmpty())
        assertNull("NextKey must be null for empty list", result.nextKey)
    }


    @Test
    fun `load should handle network exceptions`() = runTest {
        coEvery {
            mockRemoteSource.getAllProducts(any(), any(), any(), any())
        } throws Exception("Network error")

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )

        val result = pagingSource.load(params)

        assertTrue("Result must be of type Error", result is PagingSource.LoadResult.Error)
        result as PagingSource.LoadResult.Error

        assertTrue(
            "Error message must contain 'Network error'",
            result.throwable.message?.contains("Network error") == true
        )
    }


    @Test
    fun `load should handle unsuccessful response`() = runTest {
        // ARRANGE
        val errorResponse = Response.error<ProductsResponseDto>(
            404,
            ResponseBody.create(
                "application/json".toMediaTypeOrNull(),
                """{"error": "Not found"}"""
            )
        )

        coEvery {
            mockRemoteSource.getAllProducts(any(), any(), any(), any())
        } returns errorResponse

        val params = PagingSource.LoadParams.Refresh(
            key = 1,
            loadSize = 20,
            placeholdersEnabled = false
        )

        val result = pagingSource.load(params)

        assertTrue("Result must be of type Error", result is PagingSource.LoadResult.Error)
    }


    @Test
    fun `getRefreshKey should return correct page key`() {
        val state = mockk<PagingState<Int, ProductEntity>>()
        val page = mockk<PagingSource.LoadResult.Page<Int, ProductEntity>>()

        every { state.anchorPosition } returns 20
        every { state.closestPageToPosition(20) } returns page
        every { page.prevKey } returns 1
        every { page.nextKey } returns 3

        val refreshKey = pagingSource.getRefreshKey(state)

        assertEquals("RefreshKey should be centerfold", 2, refreshKey)
    }


    @Test
    fun `load should handle different sort types correctly`() = runTest {
        val sortTypes = listOf(
            SortType.TITLE_ASC to ("title" to "asc"),
            SortType.TITLE_DESC to ("title" to "desc"),
            SortType.PRICE_DESC to ("price" to "desc"),
            SortType.NONE to (null to null)
        )

        val testProducts = createTestProducts()
        val responseDto = ProductsResponseDto(
            products = testProducts,
            total = testProducts.size,
            skip = 0,
            limit = 10
        )

        for ((sortType, expectedSort) in sortTypes) {
            pagingSource = ProductsPagingSource(
                sortType = sortType,
                productsRemoteSource = mockRemoteSource,
                dispatcher = Dispatchers.Unconfined
            )

            coEvery {
                mockRemoteSource.getAllProducts(any(), any(), any(), any())
            } returns Response.success(responseDto)

            val params = PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 20,
                placeholdersEnabled = false
            )
            val result = pagingSource.load(params)

            assertTrue(
                "Result must be in Page type",
                result is PagingSource.LoadResult.Page
            )

            coVerify {
                mockRemoteSource.getAllProducts(
                    skip = any(),
                    limit = any(),
                    sortBy = expectedSort.first,
                    order = expectedSort.second
                )
            }
        }
    }
}