package com.skapps.fakestoreapp.data.network.api

import com.skapps.fakestoreapp.data.models.ProductDto
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FakeStoreApi {

    @GET("/products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
        @Query("sortBy") sortBy: String?=null,
        @Query("order") order: String?=null
    ): Response<ProductsResponseDto>


    @GET("/products/search")
    suspend fun search(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<ProductsResponseDto>

    @GET("/products/{id}")
    suspend fun getProductById(
        @Path("id") id: String
    ): Response<ProductDto>
}