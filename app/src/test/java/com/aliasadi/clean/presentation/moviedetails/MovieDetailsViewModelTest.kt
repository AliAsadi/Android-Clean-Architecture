package com.aliasadi.clean.presentation.moviedetails

import androidx.lifecycle.Observer
import com.aliasadi.clean.domain.model.Movie
import com.aliasadi.clean.domain.usecase.GetMovieDetailsUseCase
import com.aliasadi.clean.domain.util.Result
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.presentation.util.rules.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Ali Asadi on 16/05/2020
 **/
@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest : BaseViewModelTest() {

    var movieId: Int = 1413

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(movieId, getMovieDetailsUseCase, coroutineRule.testDispatcherProvider)
    }

    @Test
    fun loadInitialState_movieAvailable_showMovieDetails() = coroutineRule.runBlockingTest {

        val movieObs: Observer<Movie> = mock()

        `when`(getMovieDetailsUseCase.getMovie(movieId)).thenReturn(Result.Success(mock()))

        viewModel.getMovieLiveData().observeForever(movieObs)

        viewModel.loadInitialState()

        Mockito.verify(movieObs).onChanged(any())
    }

    @Test
    fun loadInitialState_movieNotAvailable_doNothing() = coroutineRule.runBlockingTest {
        val movieObs: Observer<Movie> = mock()

        `when`(getMovieDetailsUseCase.getMovie(movieId)).thenReturn(Result.Error(mock()))

        viewModel.getMovieLiveData().observeForever(movieObs)

        viewModel.loadInitialState()

        Mockito.verifyNoMoreInteractions(movieObs)
    }
}