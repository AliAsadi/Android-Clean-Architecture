package com.aliasadi.clean.presentation.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aliasadi.clean.domain.usecase.GetMoviesUseCase
import com.aliasadi.clean.presentation.util.DispatchersProvider

/**
 * * Created by Ali Asadi on 13/05/2020
 */
class FeedViewModelFactory(
        private val getMoviesUseCase: GetMoviesUseCase,
        private val dispatchers: DispatchersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedViewModel(getMoviesUseCase, dispatchers) as T
    }

}