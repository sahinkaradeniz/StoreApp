package com.skapps.fakestoreapp.data.di.room.basket

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skapps.fakestoreapp.data.datasource.local.basket.BasketDao
import com.skapps.fakestoreapp.data.models.cart.BasketProductsDbModel

@Database(entities = [BasketProductsDbModel::class], version = 1, exportSchema = false)
abstract class BasketDatabase: RoomDatabase() {
    abstract fun basketDao(): BasketDao
}