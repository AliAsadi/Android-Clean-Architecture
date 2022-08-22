package com.aliasadi.clean.di.details

import com.aliasadi.clean.moviedetails.MovieDetailsFragment
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [MovieDetailsModule::class])
interface MovieDetailsSubComponent {
    fun inject(movieDetailsFragment: MovieDetailsFragment)
}