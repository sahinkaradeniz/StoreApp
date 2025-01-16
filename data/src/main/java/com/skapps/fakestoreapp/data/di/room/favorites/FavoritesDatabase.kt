package com.skapps.fakestoreapp.data.di.room.favorites

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skapps.fakestoreapp.data.datasource.local.favorites.FavoritesDao
import com.skapps.fakestoreapp.data.models.favorites.FavoritesDbModel

@Database(entities = [FavoritesDbModel::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase: RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}