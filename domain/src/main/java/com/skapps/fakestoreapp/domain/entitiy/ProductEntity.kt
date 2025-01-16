package com.skapps.fakestoreapp.domain.entitiy


data class ProductEntity(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double?,
    val id: Int,
    val images: List<String>,
    val oldPrice: Double,
    val newPrice: Double,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String,
    val basketQuantity: Int = 0,
    val isFavorite: Boolean = false
)