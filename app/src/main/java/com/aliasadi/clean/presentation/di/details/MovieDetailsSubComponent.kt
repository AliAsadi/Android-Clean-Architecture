package com.aliasadi.clean.presentation.di.details

import com.aliasadi.clean.presentation.details.MovieDetailsActivity
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsSubComponent {
    fun inject(movieDetailsActivity: MovieDetailsActivity)
}