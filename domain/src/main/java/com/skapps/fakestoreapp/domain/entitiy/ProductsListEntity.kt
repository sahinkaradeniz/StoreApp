package com.skapps.fakestoreapp.domain.entitiy

data class ProductsListEntity (
    val limit: Int,
    val products: List<ProductEntity>,
    val skip: Int,
    val total: Int
)