package com.aliasadi.clean.presentation.di.feed

import com.aliasadi.clean.presentation.feed.FeedActivity
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [FeedModule::class])
interface FeedSubComponent {
    fun inject(feedActivity: FeedActivity)
}