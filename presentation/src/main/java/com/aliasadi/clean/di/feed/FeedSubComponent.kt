package com.aliasadi.clean.di.feed

import com.aliasadi.clean.ui.feed.FeedFragment
import dagger.Subcomponent

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Subcomponent(modules = [FeedModule::class])
interface FeedSubComponent {
    fun inject(feedFragment: FeedFragment)
}