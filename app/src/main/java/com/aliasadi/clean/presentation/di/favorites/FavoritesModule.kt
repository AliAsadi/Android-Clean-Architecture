package com.aliasadi.clean.presentation.di.favorites

import com.aliasadi.clean.presentation.favorites.FavoritesViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class FavoritesModule {

    @Provides
    fun provideFavoritesViewModelFactory(dispatchersProvider: DispatchersProvider): FavoritesViewModel.Factory {
        return FavoritesViewModel.Factory(dispatchersProvider)
    }

}