package com.aliasadi.clean.di.favorites

import com.aliasadi.clean.ui.favorites.FavoritesFragment
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [FavoritesModule::class])
interface FavoritesSubComponent {
    fun inject(favoritesFragment: FavoritesFragment)
}