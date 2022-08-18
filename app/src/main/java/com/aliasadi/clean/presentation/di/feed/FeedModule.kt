package com.aliasadi.clean.presentation.di.feed

import com.aliasadi.clean.presentation.feed.FeedViewModel
import com.aliasadi.clean.presentation.util.DispatchersProvider
import com.aliasadi.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class FeedModule {

    @Provides
    fun provideFeedViewModelFactory(getMoviesUseCase: GetMoviesUseCase, dispatchersProvider: DispatchersProvider): FeedViewModel.Factory {
        return FeedViewModel.Factory(getMoviesUseCase, dispatchersProvider)
    }

}