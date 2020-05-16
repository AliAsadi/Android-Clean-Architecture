package com.aliasadi.clean.presentation.di.core.module

import com.aliasadi.clean.data.api.MovieApi
import com.aliasadi.clean.data.db.MovieDao
import com.aliasadi.clean.data.repository.movie.*
import com.aliasadi.clean.domain.repository.MovieRepository
import com.aliasadi.clean.domain.usecase.GetMoviesUseCase
import com.aliasadi.clean.domain.util.DiskExecutor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieRemote: MovieDataSource.Remote,
                               movieLocal: MovieDataSource.Local,
                               movieCache: MovieDataSource.Cache): MovieRepository {
        return MovieRepositoryImpl(movieRemote, movieLocal, movieCache)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(
            executor: DiskExecutor, movieDao: MovieDao
    ): MovieDataSource.Local {
        return MovieLocalDataSource(executor, movieDao)
    }

    @Provides
    @Singleton
    fun provideMovieCacheDataSource(): MovieDataSource.Cache {
        return MovieCacheDataSource()
    }


    @Provides
    @Singleton
    fun provideMovieRemoveDataSource(movieApi: MovieApi): MovieDataSource.Remote {
        return MovieRemoteDataSource(movieApi)
    }

    @Provides
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMoviesUseCase {
        return GetMoviesUseCase(movieRepository)
    }
}