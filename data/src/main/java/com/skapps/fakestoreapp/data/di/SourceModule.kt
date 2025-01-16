package com.skapps.fakestoreapp.data.di

import com.skapps.fakestoreapp.data.datasource.local.basket.BasketLocalDataSource
import com.skapps.fakestoreapp.data.datasource.local.basket.BasketLocalDataSourceImpl
import com.skapps.fakestoreapp.data.datasource.local.favorites.FavoritesLocalDataSource
import com.skapps.fakestoreapp.data.datasource.local.favorites.FavoritesLocalDataSourceImpl
import com.skapps.fakestoreapp.data.datasource.paging.ProductsPagingSource
import com.skapps.fakestoreapp.data.datasource.paging.SearchProductsPagingSource
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSource
import com.skapps.fakestoreapp.data.datasource.remote.ProductsRemoteSourceImpl
import com.skapps.fakestoreapp.domain.entitiy.SortType
import dagger.Binds
import dagger.Module
import dagger.assisted.AssistedFactory
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {
    @Binds
    @Singleton
    abstract fun bindProductsListRemoteSource(productsRemoteSourceImpl: ProductsRemoteSourceImpl): ProductsRemoteSource

    @Binds
    @Singleton
    abstract fun bindFavoritesLocalDataSource(favoritesLocalDataSourceImpl: FavoritesLocalDataSourceImpl): FavoritesLocalDataSource


    @Binds
    @Singleton
    abstract fun bindBasketLocalDataSource(basketLocalDataSourceImpl: BasketLocalDataSourceImpl): BasketLocalDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @AssistedFactory
    interface ProductsPagingSourceFactory {
        fun create(sortType: SortType): ProductsPagingSource
    }


    @AssistedFactory
    interface SearchProductsPagingSourceFactory {
        fun create(query: String): SearchProductsPagingSource
    }

}
