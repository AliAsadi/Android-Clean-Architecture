package com.aliasadi.clean.di

import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.db.movies.MovieRemoteKeyDao
import com.aliasadi.data.repository.movie.*
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesLocalDataSource
import com.aliasadi.data.util.DiskExecutor
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.mock.MockMovieRemoteDataSource
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
        return MockMovieRemoteDataSource(movieApi)
    }
}