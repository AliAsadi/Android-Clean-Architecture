package com.aliasadi.clean.presentation.feed

import androidx.lifecycle.Observer
import com.aliasadi.clean.feed.FeedViewModel
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.presentation.util.rules.runBlockingTest
import com.aliasadi.domain.entities.Movie
import com.aliasadi.domain.usecase.GetMovies
import com.aliasadi.domain.util.Result
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
class FeedViewModelTest : BaseViewModelTest() {

    @Mock
    lateinit var getMovies: GetMovies

    private lateinit var viewModel: FeedViewModel

    @Before
    fun setUp() {
        viewModel = FeedViewModel(getMovies, coroutineRule.testDispatcherProvider)
    }

    @Test
    fun onInitialState_loadMovies_onSuccess_hideLoadingAndShowMovies() = coroutineRule.runBlockingTest {
        val movieObs: Observer<List<Movie>> = mock()
        val showLoadingObs: Observer<Unit> = mock()
        val hideLoadingObs: Observer<Unit> = mock()

        viewModel.getMoviesLiveData().observeForever(movieObs)
        viewModel.getShowLoadingLiveData().observeForever(showLoadingObs)
        viewModel.getHideLoadingLiveData().observeForever(hideLoadingObs)

        Mockito.`when`(getMovies.getMovies()).thenReturn(Result.Success(mock()))

        viewModel.onInitialState()

        val inOrder = Mockito.inOrder(showLoadingObs, hideLoadingObs, movieObs)

        inOrder.verify(showLoadingObs).onChanged(Unit)
        inOrder.verify(hideLoadingObs).onChanged(Unit)
        inOrder.verify(movieObs).onChanged(any())
    }

    @Test
    fun onInitialState_loadMovies_onFailure_hideLoadingAndShowErrorMessage() = coroutineRule.runBlockingTest {
        val movieObs: Observer<List<Movie>> = mock()
        val errorObs: Observer<String> = mock()
        val showLoadingObs: Observer<Unit> = mock()
        val hideLoadingObs: Observer<Unit> = mock()

        viewModel.getMoviesLiveData().observeForever(movieObs)
        viewModel.getShowErrorLiveData().observeForever(errorObs)
        viewModel.getShowLoadingLiveData().observeForever(showLoadingObs)
        viewModel.getHideLoadingLiveData().observeForever(hideLoadingObs)

        Mockito.`when`(getMovies.getMovies()).thenReturn(Result.Error(mock()))

        viewModel.onInitialState()

        val inOrder = Mockito.inOrder(showLoadingObs, hideLoadingObs, errorObs)

        inOrder.verify(showLoadingObs).onChanged(Unit)
        inOrder.verify(hideLoadingObs).onChanged(Unit)
        inOrder.verify(errorObs).onChanged(any())
        Mockito.verifyNoMoreInteractions(movieObs)
    }


    @Test
    fun onInitialState_loadMovies_onLoading_showLoadingView() = coroutineRule.runBlockingTest {
        val showLoadingObs: Observer<Unit> = mock()
        viewModel.getShowLoadingLiveData().observeForever(showLoadingObs)
        viewModel.onInitialState()
        Mockito.verify(showLoadingObs).onChanged(Unit)
    }


    @Test
    fun onMovieClicked_navigateToMovieDetails() {
        val navigateObs: Observer<Movie> = mock()
        val movie: Movie = mock()

        viewModel.getNavigateToMovieDetails().observeForever(navigateObs)

        viewModel.onMovieClicked(movie)

        Mockito.verify(navigateObs).onChanged(any())
    }

}