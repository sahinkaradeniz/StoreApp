package com.skapps.fakestoreapp.data.mapper

import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel
import com.skapps.fakestoreapp.domain.entitiy.ProductEntity
import com.skapps.fakestoreapp.domain.entitiy.favorites.FavoritesEntity

fun FavoritesDbModel.toEntity(): FavoritesEntity {
    return FavoritesEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        images = this.images,
        newPrice = this.newPrice,
        oldPrice = this.oldPrice
    )
}


fun FavoritesEntity.toDbModel(): FavoritesDbModel {
    return FavoritesDbModel(
        id = this.id,
        description = this.description,
        images = this.images,
        newPrice = this.newPrice,
        title = this.title,
        oldPrice = this.oldPrice
    )
}


fun ProductEntity.toDbModel(): FavoritesDbModel {
    return FavoritesDbModel(
        id = this.id,
        description = this.description,
        images = this.images.firstOrNull() ?: "",
        newPrice = this.newPrice,
        title = this.title,
        oldPrice = this.oldPrice
    )
}