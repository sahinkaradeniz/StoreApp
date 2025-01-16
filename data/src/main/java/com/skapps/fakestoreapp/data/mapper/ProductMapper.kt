package com.skapps.fakestoreapp.data.mapper

import com.skapps.fakestoreapp.data.models.ProductDto
import com.skapps.fakestoreapp.data.models.ProductsResponseDto
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.ProductsListEntity


import java.math.BigDecimal
import java.math.RoundingMode

fun ProductDto.toEntity(): ProductEntity {
    val safePrice = price ?: 0.0
    val safeDiscount = discountPercentage ?: 0.0
    val newPrice = safePrice - (safePrice * safeDiscount / 100)

    val formattedOldPrice = BigDecimal(safePrice).setScale(2, RoundingMode.HALF_UP).toDouble()
    val formattedNewPrice = BigDecimal(newPrice).setScale(2, RoundingMode.HALF_UP).toDouble()

    return ProductEntity(
        brand = brand ?: "Unknown Brand",
        category = category ?: "Unknown Category",
        description = description ?: "No description available",
        discountPercentage = safeDiscount,
        id = id ?: -1,
        images = images ?: emptyList(),
        oldPrice = formattedOldPrice,
        newPrice = formattedNewPrice,
        rating = rating ?: 0.0,
        stock = stock ?: 0,
        thumbnail = thumbnail ?: "",
        title = title ?: "Unnamed Product",
        basketQuantity = 0,
        isFavorite = false
    )
}




fun ProductsResponseDto.toEntity(): ProductsListEntity {
    return ProductsListEntity(
        limit = limit,
        products = products.map { it.toEntity() },
        skip = skip,
        total = total
    )
}




