package com.aliasadi.clean.di.favorites

import com.aliasadi.clean.favorites.FavoritesViewModel
import com.aliasadi.domain.usecase.GetFavoriteMovies
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class FavoritesModule {

    @Provides
    fun provideFavoritesViewModelFactory(
        dispatchersProvider: com.aliasadi.data.util.DispatchersProvider,
        getFavoriteMovies: GetFavoriteMovies
    ): FavoritesViewModel.Factory {
        return FavoritesViewModel.Factory(getFavoriteMovies, dispatchersProvider)
    }

}