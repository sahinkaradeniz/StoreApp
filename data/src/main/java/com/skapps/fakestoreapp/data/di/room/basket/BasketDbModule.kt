package com.skapps.fakestoreapp.data.di.room.basket

import android.content.Context
import androidx.room.Room
import com.skapps.fakestoreapp.data.datasource.local.basket.BasketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object BasketDbModule {

    @Provides
    fun provideBasketDb(
        @ApplicationContext context: Context
    ): BasketDatabase = Room.databaseBuilder(
        context, BasketDatabase::class.java,
        "basket_db"
    ).build()

    @Provides
    fun provideBasketDao(database: BasketDatabase): BasketDao = database.basketDao()
}