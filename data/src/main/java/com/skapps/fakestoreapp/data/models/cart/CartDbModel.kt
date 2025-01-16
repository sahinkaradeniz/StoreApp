package com.skapps.fakestoreapp.data.models.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skapps.fakestoreapp.domain.entitiy.basket.BasketProductEntity

@Entity(tableName = "basket_table")
data class BasketProductsDbModel(
    @PrimaryKey
    val id: Int,
    val image: String,
    val price: Double,
    val oldPrice: Double,
    val title: String,
    val quantity: Int
)


fun BasketProductsDbModel.toEntity() = BasketProductEntity(
    id = id,
    image = image,
    price = price,
    oldPrice = oldPrice,
    title = title,
    quantity = quantity
)


fun BasketProductEntity.toDbModel() = BasketProductsDbModel(
    id = id,
    image = image,
    price = price,
    oldPrice = oldPrice,
    title = title,
    quantity = quantity
)