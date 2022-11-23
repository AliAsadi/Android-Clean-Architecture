package com.aliasadi.clean.presentation.feed

import androidx.lifecycle.Observer
import com.aliasadi.clean.presentation.base.BaseViewModelTest
import com.aliasadi.clean.presentation.util.rules.runBlockingTest
import com.aliasadi.clean.ui.feed.FeedViewModel
import com.aliasadi.clean.ui.states.AllStatesUtil
import com.aliasadi.clean.ui.states.NavigationState
import com.aliasadi.domain.usecase.GetMovies
import com.aliasadi.domain.util.Result
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito.*
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
        viewModel = FeedViewModel(
            getMovies = getMovies,
            dispatchers = coroutineRule.testDispatcherProvider
        )
    }

    @Test
    fun onInitialState_loadMovies_onSuccess_hideLoadingAndShowMovies() = coroutineRule.runBlockingTest {
        val uiStateObs: Observer<AllStatesUtil> = mock()

        viewModel.getUiState().observeForever(uiStateObs)

        `when`(getMovies.execute()).thenReturn(Result.Success(listOf()))

        viewModel.onInitialState()

        val argumentCapture = ArgumentCaptor.forClass(AllStatesUtil::class.java)
        verify(uiStateObs, times(3)).onChanged(argumentCapture.capture())

        assert(argumentCapture.allValues[0] is AllStatesUtil.Loading)
        assert(argumentCapture.allValues[1] is AllStatesUtil.NotLoading)
        assert(argumentCapture.allValues[2] is AllStatesUtil.FeedUiState)
    }

    @Test
    fun onInitialState_loadMovies_onFailure_hideLoadingAndShowErrorMessage() = coroutineRule.runBlockingTest {
        val uiStateObs: Observer<AllStatesUtil> = mock()

        viewModel.getUiState().observeForever(uiStateObs)


        `when`(getMovies.execute()).thenReturn(Result.Error(mock()))

        viewModel.onInitialState()

        val argumentCapture = ArgumentCaptor.forClass(AllStatesUtil::class.java)
        verify(uiStateObs, times(3)).onChanged(argumentCapture.capture() as AllStatesUtil.UiState?)

        assert(argumentCapture.allValues[0] is AllStatesUtil.Loading)
        assert(argumentCapture.allValues[1] is AllStatesUtil.NotLoading)
        assert(true)
    }


    @Test
    fun onInitialState_loadMovies_onLoading_showLoadingView() = coroutineRule.runBlockingTest {
        val uiStateObs: Observer<AllStatesUtil> = mock()
        viewModel.getUiState().observeForever(uiStateObs)

        viewModel.onInitialState()

        verify(uiStateObs).onChanged(isA(AllStatesUtil.Loading.javaClass))
    }


    @Test
    fun onMovieClicked_navigateToMovieDetails() {
        val navigateObs: Observer<NavigationState> = mock()
        val movieId = 1

        viewModel.getNavigationState().observeForever(navigateObs)

        viewModel.onMovieClicked(movieId)

        verify(navigateObs).onChanged(isA(NavigationState.MovieDetails::class.java))
    }

}