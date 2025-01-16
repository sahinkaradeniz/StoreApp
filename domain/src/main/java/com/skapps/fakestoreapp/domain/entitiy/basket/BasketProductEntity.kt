package com.skapps.fakestoreapp.domain.entitiy.basket

data class BasketProductEntity (
    val id: Int,
    val image: String,
    val price: Double,
    val oldPrice: Double,
    val title: String,
    val quantity: Int
)