package com.aliasadi.clean.presentation.di

import com.aliasadi.clean.presentation.di.details.MovieDetailsSubComponent
import com.aliasadi.clean.presentation.di.feed.FeedSubComponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
interface DaggerInjector {
    fun createDetailsComponent(): MovieDetailsSubComponent
    fun createFeedComponent(): FeedSubComponent
}