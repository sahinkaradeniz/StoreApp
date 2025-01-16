package com.skapps.fakestoreapp.di

import com.skapps.fakestoreapp.core.loading.GlobalLoadingManager
import com.skapps.fakestoreapp.core.loading.GlobalLoadingManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoadingModule {

    @Binds
    @Singleton
    abstract fun bindGlobalLoadingManager(globalLoadingManager: GlobalLoadingManagerImpl): GlobalLoadingManager
}