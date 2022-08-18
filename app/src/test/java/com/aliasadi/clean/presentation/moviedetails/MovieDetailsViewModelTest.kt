package com.aliasadi.clean.presentation.moviedetails

import androidx.lifecycle.Observer
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.presentation.moviedetails.MovieDetailsViewModel.MovieDetailsUiState
import com.aliasadi.clean.presentation.util.ResourceProvider
import com.aliasadi.clean.presentation.util.rules.runBlockingTest
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.usecase.AddMovieToFavoriteUseCase
import com.aliasadi.domain.usecase.CheckFavoriteStatusUseCase
import com.aliasadi.domain.usecase.GetMovieDetailsUseCase
import com.aliasadi.domain.usecase.RemoveMovieFromFavoriteUseCase
import com.aliasadi.domain.util.Result
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

    private var movieId: Int = 1413

    private val movie = Movie(movieId, "", "", "")

    @Mock
    lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    @Mock
    lateinit var checkFavoriteStatusUseCase: CheckFavoriteStatusUseCase

    @Mock
    lateinit var addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase

    @Mock
    lateinit var removeMovieFromFavoriteUseCase: RemoveMovieFromFavoriteUseCase

    @Mock
    lateinit var resourceProvider: ResourceProvider

    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(
            movieId = movieId,
            getMovieDetailsUseCase = getMovieDetailsUseCase,
            checkFavoriteStatusUseCase = checkFavoriteStatusUseCase,
            removeMovieFromFavoriteUseCase = removeMovieFromFavoriteUseCase,
            addMovieToFavoriteUseCase = addMovieToFavoriteUseCase,
            resourceProvider = resourceProvider,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_movieAvailable_showMovieDetails() = coroutineRule.runBlockingTest {

        val movieObs: Observer<MovieDetailsUiState> = mock()

        `when`(getMovieDetailsUseCase.getMovie(movieId)).thenReturn(Result.Success(movie))

        viewModel.getMovieDetailsUiStateLiveData().observeForever(movieObs)

        viewModel.onInitialState()

        Mockito.verify(movieObs).onChanged(any())
    }

    @Test
    fun onInitialState_movieNotAvailable_doNothing() = coroutineRule.runBlockingTest {
        val movieObs: Observer<MovieDetailsUiState> = mock()

        `when`(getMovieDetailsUseCase.getMovie(movieId)).thenReturn(Result.Error(mock()))

        viewModel.getMovieDetailsUiStateLiveData().observeForever(movieObs)

        viewModel.onInitialState()

        Mockito.verifyNoMoreInteractions(movieObs)
    }
}