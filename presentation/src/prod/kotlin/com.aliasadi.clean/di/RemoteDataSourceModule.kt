package com.aliasadi.clean.di

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.repository.movie.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 24/05/2024
 **/
@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {
    @Provides
    @Singleton
    fun provideMovieRemoveDataSource(movieApi: MovieApi): MovieDataSource.Remote {
        return MovieRemoteDataSource(movieApi)
    }
}