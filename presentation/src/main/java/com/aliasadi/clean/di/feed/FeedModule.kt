package com.aliasadi.clean.di.feed

import com.aliasadi.clean.feed.FeedViewModel
import com.aliasadi.domain.usecase.GetMoviesUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class FeedModule {

    @Provides
    fun provideFeedViewModelFactory(getMoviesUseCase: GetMoviesUseCase, dispatchersProvider: com.aliasadi.data.util.DispatchersProvider): FeedViewModel.Factory {
        return FeedViewModel.Factory(getMoviesUseCase, dispatchersProvider)
    }

}