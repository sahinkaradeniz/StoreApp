package com.skapps.fakestoreapp.domain.entitiy.favorites

data class FavoritesEntity(
    val id: Int,
    val description: String,
    val images: String,
    val newPrice: Double,
    val title: String,
    val oldPrice: Double,
)
