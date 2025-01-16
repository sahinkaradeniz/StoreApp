package com.skapps.fakestoreapp.data.api

import com.google.common.truth.Truth.assertThat
import com.google.gson.GsonBuilder
import com.skapps.fakestoreapp.data.network.api.FakeStoreApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FakeStoreApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: FakeStoreApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        api = retrofit.create(FakeStoreApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAllProducts returns successful response`() = runBlocking {
        val mockJson = """
            {
              "products": [
                {
                  "id": 1,
                  "title": "Test Product",
                  "price": 99.99
                }
              ],
              "total": 1,
              "skip": 0,
              "limit": 10
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val response = api.getAllProducts(
            limit = 10,
            skip = 1,
            sortBy = null,
            order = null
        )


        assertThat(response.isSuccessful).isTrue()

        assertThat(response.body()).isNotNull()

        val productsResponseDto = response.body()


        assertThat(productsResponseDto?.total).isEqualTo(1)

        assertThat(productsResponseDto?.products?.size).isEqualTo(1)

        val product = productsResponseDto?.products?.first()

        assertThat(product?.id).isEqualTo(1)

        assertThat(product?.title).isEqualTo("Test Product")


        assertThat(product?.price ?: 0.0)
            .isWithin(0.001)
            .of(99.99)
    }

    @Test
    fun `getAllProducts returns multiple products`() = runBlocking {
        // -- Arrange --
        val mockJson = """
            {
              "products": [
                {
                  "id": 1,
                  "title": "Phone",
                  "price": 500.0
                },
                {
                  "id": 2,
                  "title": "Laptop",
                  "price": 1200.0
                }
              ],
              "total": 2,
              "skip": 0,
              "limit": 10
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val response = api.getAllProducts(limit = 10, skip = 1, sortBy = null, order = null)

        assertThat(response.isSuccessful).isTrue()
        val productsResponseDto = response.body()
        assertThat(productsResponseDto).isNotNull()

        assertThat(productsResponseDto?.total).isEqualTo(2)
        assertThat(productsResponseDto?.products?.size).isEqualTo(2)

        val firstProduct = productsResponseDto?.products?.get(0)
        assertThat(firstProduct?.id).isEqualTo(1)
        assertThat(firstProduct?.title).isEqualTo("Phone")
        assertThat(firstProduct?.price ?: 0.0)
            .isWithin(0.001)
            .of(500.0)

        val secondProduct = productsResponseDto?.products?.get(1)
        assertThat(secondProduct?.id).isEqualTo(2)
        assertThat(secondProduct?.title).isEqualTo("Laptop")
        assertThat(secondProduct?.price ?: 0.0)
            .isWithin(0.001)
            .of(1200.0)
    }

    @Test
    fun `getAllProducts returns 404 error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .setBody("")
        )

        val response = api.getAllProducts(limit = 10, skip = 0, sortBy = null, order = null)


        assertThat(response.isSuccessful).isFalse()

        assertThat(response.body()).isNull()
    }

    @Test
    fun `getAllProducts returns empty product list`() = runBlocking {
        val mockJson = """
            {
              "products": [],
              "total": 0,
              "skip": 0,
              "limit": 10
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val response = api.getAllProducts(limit = 10, skip = 0, sortBy = null, order = null)

        assertThat(response.isSuccessful).isTrue()
        val productsResponseDto = response.body()
        assertThat(productsResponseDto).isNotNull()


        assertThat(productsResponseDto?.total).isEqualTo(0)

        assertThat(productsResponseDto?.products?.size).isEqualTo(0)
    }

    @Test
    fun `getAllProducts returns product with null price`() = runBlocking {
        // -- Arrange --
        val mockJson = """
            {
              "products": [
                {
                  "id": 10,
                  "title": "Null Price Product",
                  "price": null
                }
              ],
              "total": 1,
              "skip": 0,
              "limit": 10
            }
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(mockJson)
        )

        val response = api.getAllProducts(limit = 10, skip = 0, sortBy = null, order = null)

        assertThat(response.isSuccessful).isTrue()
        val productsResponseDto = response.body()
        assertThat(productsResponseDto).isNotNull()


        assertThat(productsResponseDto?.total).isEqualTo(1)
        assertThat(productsResponseDto?.products?.size).isEqualTo(1)

        val product = productsResponseDto?.products?.first()
        assertThat(product?.id).isEqualTo(10)
        assertThat(product?.title).isEqualTo("Null Price Product")

        assertThat(product?.price ?: 0.0)
            .isWithin(0.001)
            .of(0.0)
    }
}