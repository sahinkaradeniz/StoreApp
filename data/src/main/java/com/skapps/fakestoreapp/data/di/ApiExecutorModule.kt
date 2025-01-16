package com.skapps.fakestoreapp.data.di

import com.skapps.fakestoreapp.data.network.apiexecutor.ApiExecutor
import com.skapps.fakestoreapp.data.network.apiexecutor.ApiExecutorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ApiExecutorModule {

    @Binds
    abstract fun bindApiExecutor(
        apiExecutorImpl: ApiExecutorImpl
    ): ApiExecutor
}