package com.skapps.fakestoreapp.data.datasource

import com.google.common.truth.Truth.assertThat
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSource
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSourceImpl
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import com.skapps.fakestoreapp.data.network.api.FakeStoreApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

class ProductsRemoteSourceImplUnitTest {

    private lateinit var fakeStoreApi: FakeStoreApi
    private lateinit var productsRemoteSource: ProductsRemoteSource

    @Before
    fun setUp() {
        fakeStoreApi = Mockito.mock(FakeStoreApi::class.java)
        productsRemoteSource = ProductsRemoteSourceImpl(fakeStoreApi)
    }

    @Test
    fun `getAllProducts should return the same response as FakeStoreApi`() = runBlocking {
        val mockDto = ProductsResponseDto(
            products = emptyList(),
            total = 0,
            skip = 0,
            limit = 10
        )
        val mockResponse = Response.success(mockDto)
        `when`(fakeStoreApi.getAllProducts(10, 0, null, null)).thenReturn(mockResponse)

        val actualResponse = productsRemoteSource.getAllProducts(10, 0, null, null)


        Mockito.verify(fakeStoreApi).getAllProducts(10, 0, null, null)


        assertThat(actualResponse)
            .isSameInstanceAs(mockResponse)

        assertThat(actualResponse.body())
            .isEqualTo(mockDto)
    }

    @Test
    fun `search should return the same response as FakeStoreApi`() = runBlocking {
        val mockDto = ProductsResponseDto(
            products = emptyList(),
            total = 0,
            skip = 0,
            limit = 10
        )
        val mockResponse = Response.success(mockDto)
        `when`(fakeStoreApi.search("phone", 5, 0)).thenReturn(mockResponse)


        val actualResponse = productsRemoteSource.search("phone", 5, 0)


        Mockito.verify(fakeStoreApi).search("phone", 5, 0)

        assertThat(actualResponse).isSameInstanceAs(mockResponse)
        assertThat(actualResponse.body()).isEqualTo(mockDto)
    }
}