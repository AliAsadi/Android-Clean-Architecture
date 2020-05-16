package com.aliasadi.clean.presentation.di.details

import com.aliasadi.clean.presentation.details.MovieDetailsViewModelFactory
import dagger.Module
import dagger.Provides
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieDetailsViewModelFactory(dispatchersProvider: DispatchersProvider): MovieDetailsViewModelFactory {
        return MovieDetailsViewModelFactory(dispatchersProvider)
    }
}