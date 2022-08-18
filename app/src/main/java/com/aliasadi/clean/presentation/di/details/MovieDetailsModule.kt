package com.aliasadi.clean.presentation.di.details

import com.aliasadi.clean.presentation.moviedetails.MovieDetailsViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
import com.aliasadi.clean.presentation.util.ResourceProvider
import com.aliasadi.domain.usecase.AddMovieToFavoriteUseCase
import com.aliasadi.domain.usecase.CheckFavoriteStatusUseCase
import com.aliasadi.domain.usecase.GetMovieDetailsUseCase
import com.aliasadi.domain.usecase.RemoveMovieFromFavoriteUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieDetailsViewModelFactory(
        getMovieDetailsUseCase: GetMovieDetailsUseCase,
        checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase,
        addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
        removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase,
        dispatchersProvider: DispatchersProvider,
        resourceProvider: ResourceProvider
    ): MovieDetailsViewModel.Factory {
        return MovieDetailsViewModel.Factory(
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            addMovieToFavoriteUseCase = addMovieToFavoriteUseCase,
            checkFavoriteStatusUseCase = checkFavoriteStatusUseCase,
            removeMovieFromFavoriteUseCase = removeMovieFromFavoriteUseCase,
            resourceProvider = resourceProvider,
            dispatchers = dispatchersProvider
        )
    }
}