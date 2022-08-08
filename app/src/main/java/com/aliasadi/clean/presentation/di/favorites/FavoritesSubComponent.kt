package com.aliasadi.clean.presentation.di.favorites

import com.aliasadi.clean.presentation.favorites.FavoritesFragment
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesSubComponent {
    fun inject(favoritesFragment: FavoritesFragment)
}