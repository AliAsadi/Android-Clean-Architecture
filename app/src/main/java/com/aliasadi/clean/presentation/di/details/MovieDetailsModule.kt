package com.aliasadi.clean.presentation.di.details

import com.aliasadi.clean.domain.usecase.GetMovieDetailsUseCase
import com.aliasadi.clean.presentation.details.MovieDetailsViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
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
        dispatchersProvider: DispatchersProvider
    ): MovieDetailsViewModel.Factory {
        return MovieDetailsViewModel.Factory(getMovieDetailsUseCase, dispatchersProvider)
    }
}