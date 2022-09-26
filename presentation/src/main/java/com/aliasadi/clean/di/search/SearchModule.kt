package com.aliasadi.clean.di.search

import com.aliasadi.clean.search.SearchViewModel
import com.aliasadi.data.util.DispatchersProvider
import com.aliasadi.domain.usecase.SearchMovies
import dagger.Module
import dagger.Provides

/**
 * Created by Ali Asadi on 15/05/2020
 **/
@Module
class SearchModule {

    @Provides
    fun provideSearchViewModelFactory(
        dispatchersProvider: DispatchersProvider,
        searchMovies: SearchMovies
    ): SearchViewModel.Factory {
        return SearchViewModel.Factory(dispatchersProvider, searchMovies)
    }

}