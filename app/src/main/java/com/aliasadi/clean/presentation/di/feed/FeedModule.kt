package com.aliasadi.clean.presentation.di.feed

import com.aliasadi.clean.domain.usecase.GetMoviesUseCase
import com.aliasadi.clean.presentation.feed.FeedViewModelFactory
import dagger.Module
import dagger.Provides
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class FeedModule {

    @Provides
    fun provideFeedViewModelFactory(getMoviesUseCase: GetMoviesUseCase, dispatchersProvider: DispatchersProvider): FeedViewModelFactory {
        return FeedViewModelFactory(getMoviesUseCase, dispatchersProvider)
    }

}