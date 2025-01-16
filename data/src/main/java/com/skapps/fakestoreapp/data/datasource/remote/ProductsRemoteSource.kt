package com.skapps.fakestoreapp.data.datasource.remote
import com.skapps.fakestoreapp.data.models.ProductDto
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import retrofit2.Response

interface ProductsRemoteSource {
    suspend fun getAllProducts(limit: Int, skip: Int,sortBy: String?=null,order: String?=null): Response<ProductsResponseDto>
    suspend fun search(query: String, limit: Int, skip: Int): Response<ProductsResponseDto>
    suspend fun getProductById(id: String): Response<ProductDto>
}