package com.aliasadi.clean.di.module

import android.content.Context
import com.aliasadi.data.api.MovieApi
import com.aliasadi.data.db.favoritemovies.FavoriteMovieDao
import com.aliasadi.data.db.movies.MovieDao
import com.aliasadi.data.db.movies.MovieRemoteKeyDao
import com.aliasadi.data.repository.movie.MovieDataSource
import com.aliasadi.data.repository.movie.MovieLocalDataSource
import com.aliasadi.data.repository.movie.MovieRemoteDataSource
import com.aliasadi.data.repository.movie.MovieRemoteMediator
import com.aliasadi.data.repository.movie.MovieRepositoryImpl
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesDataSource
import com.aliasadi.data.repository.movie.favorite.FavoriteMoviesLocalDataSource
import com.aliasadi.data.util.NetworkMonitorImpl
import com.aliasadi.domain.repository.MovieRepository
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetFavoriteMovies
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.aliasadi.domain.usecase.SearchMovies
import com.aliasadi.domain.util.NetworkMonitor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        movieRemote: MovieDataSource.Remote,
        movieLocal: MovieDataSource.Local,
        movieRemoteMediator: MovieRemoteMediator,
        favoriteLocal: FavoriteMoviesDataSource.Local,
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemote, movieLocal, movieRemoteMediator, favoriteLocal)
    }

    @Provides
    @Singleton
    fun provideMovieRemoveDataSource(movieApi: MovieApi): MovieDataSource.Remote {
        return MovieRemoteDataSource(movieApi)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
        movieDao: MovieDao,
        movieRemoteKeyDao: MovieRemoteKeyDao,
    ): MovieDataSource.Local {
        return MovieLocalDataSource(movieDao, movieRemoteKeyDao)
    }

    @Provides
    @Singleton
    fun provideMovieMediator(
        movieLocalDataSource: MovieDataSource.Local,
        movieRemoteDataSource: MovieDataSource.Remote
    ): MovieRemoteMediator {
        return MovieRemoteMediator(movieLocalDataSource, movieRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideFavoriteMovieLocalDataSource(
        favoriteMovieDao: FavoriteMovieDao
    ): FavoriteMoviesDataSource.Local {
        return FavoriteMoviesLocalDataSource(favoriteMovieDao)
    }

    @Provides
    fun provideSearchMoviesUseCase(movieRepository: MovieRepository): SearchMovies {
        return SearchMovies(movieRepository)
    }

    @Provides
    fun provideGetMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetails {
        return GetMovieDetails(movieRepository)
    }

    @Provides
    fun provideGetFavoriteMoviesUseCase(movieRepository: MovieRepository): GetFavoriteMovies {
        return GetFavoriteMovies(movieRepository)
    }

    @Provides
    fun provideCheckFavoriteStatusUseCase(movieRepository: MovieRepository): CheckFavoriteStatus {
        return CheckFavoriteStatus(movieRepository)
    }

    @Provides
    fun provideAddMovieToFavoriteUseCase(movieRepository: MovieRepository): AddMovieToFavorite {
        return AddMovieToFavorite(movieRepository)
    }

    @Provides
    fun provideRemoveMovieFromFavoriteUseCase(movieRepository: MovieRepository): RemoveMovieFromFavorite {
        return RemoveMovieFromFavorite(movieRepository)
    }

    @Provides
    fun provideNetworkMonitor(
        @ApplicationContext context: Context
    ): NetworkMonitor = NetworkMonitorImpl(context)
}