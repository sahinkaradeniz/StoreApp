package com.skapps.fakestoreapp.data.models.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class FavoritesDbModel(
    @PrimaryKey
    val id: Int,
    val description: String,
    val images: String,
    val newPrice: Double,
    val title: String,
    val oldPrice: Double,
)