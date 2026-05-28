package com.aliasadi.clean.di.module

import com.aliasadi.data.repository.movie.MockMovieRemoteDataSource
import com.aliasadi.data.repository.movie.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(): MovieDataSource.Remote {
        return MockMovieRemoteDataSource()
    }
}
