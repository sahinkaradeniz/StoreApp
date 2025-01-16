package com.skapps.fakestoreapp.di

import com.skapps.fakestoreapp.data.repository.BasketRepositoryImpl
import com.skapps.fakestoreapp.data.repository.FavoriteProductsRepositoryImpl
import com.skapps.fakestoreapp.data.repository.ProductsRepositoryImpl
import com.skapps.fakestoreapp.domain.repository.BasketRepository
import com.skapps.fakestoreapp.domain.repository.FavoriteProductsRepository
import com.skapps.fakestoreapp.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductsRepositoryImpl
    ): ProductsRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteProductRepository(
        favoriteProductsRepositoryImpl: FavoriteProductsRepositoryImpl
    ): FavoriteProductsRepository

    @Binds
    @Singleton
    abstract fun bindFBasketRepository(
        basketRepositoryImpl: BasketRepositoryImpl
    ): BasketRepository
}