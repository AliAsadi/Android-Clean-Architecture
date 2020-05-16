package com.aliasadi.clean.presentation.details

import android.arch.lifecycle.Observer
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Ali Asadi on 16/05/2020
 **/
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var movie: Movie

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(movie, coroutineRule.testDispatcherProvider)
    }

    @Test
    fun loadInitialState_movieAvailable_showMovieDetails() {
        val movieObs: Observer<Movie> = mock()

        viewModel.getMovieLiveData().observeForever(movieObs)

        viewModel.loadInitialState()

        Mockito.verify(movieObs).onChanged(movie)
    }

    @Test
    fun loadInitialState_movieNotAvailable_doNothing() {
        viewModel = MovieDetailsViewModel(null, coroutineRule.testDispatcherProvider)

        val movieObs: Observer<Movie> = mock()

        viewModel.getMovieLiveData().observeForever(movieObs)

        viewModel.loadInitialState()

        Mockito.verifyNoMoreInteractions(movieObs)
    }
}