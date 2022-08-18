package com.aliasadi.clean.di.details

import com.aliasadi.clean.moviedetails.MovieDetailsViewModel
import com.aliasadi.clean.util.ResourceProvider
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class MovieDetailsModule {

    @Provides
    fun provideMovieDetailsViewModelFactory(
        getMovieDetails: GetMovieDetails,
        checkFavoriteStatus: CheckFavoriteStatus,
        addMovieToFavorite: AddMovieToFavorite,
        removeMovieFromFavorite: RemoveMovieFromFavorite,
        dispatchersProvider: com.aliasadi.data.util.DispatchersProvider,
        resourceProvider: ResourceProvider
    ): MovieDetailsViewModel.Factory {
        return MovieDetailsViewModel.Factory(
            getMovieDetails = getMovieDetails,
            addMovieToFavorite = addMovieToFavorite,
            checkFavoriteStatus = checkFavoriteStatus,
            removeMovieFromFavorite = removeMovieFromFavorite,
            resourceProvider = resourceProvider,
            dispatchers = dispatchersProvider
        )
    }
}