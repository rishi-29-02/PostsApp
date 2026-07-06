package com.rm.postapp.di

import com.rm.postapp.data.repository.PostRepositoryImpl
import com.rm.postapp.data.utils.NetworkMonitorImpl
import com.rm.postapp.domain.repository.PostRepository
import com.rm.postapp.domain.utils.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(networkMonitorImpl: NetworkMonitorImpl): NetworkMonitor
}