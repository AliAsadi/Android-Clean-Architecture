package com.aliasadi.clean.di.module

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.repository.movie.MovieDataSource
import com.aliasadi.data.repository.movie.MovieRemoteDataSource
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
    fun provideMovieRemoteDataSource(movieApi: MovieApi): MovieDataSource.Remote {
        return MovieRemoteDataSource(movieApi)
    }
}
