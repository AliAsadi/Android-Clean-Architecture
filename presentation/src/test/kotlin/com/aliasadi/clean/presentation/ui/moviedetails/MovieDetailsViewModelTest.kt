package com.aliasadi.clean.presentation.ui.moviedetails

import app.cash.turbine.test
import com.aliasadi.clean.ui.moviedetails.MovieDetailsBundle
import com.aliasadi.clean.ui.moviedetails.MovieDetailsState
import com.aliasadi.clean.ui.moviedetails.MovieDetailsViewModel
import com.aliasadi.core.test.base.BaseTest
import com.aliasadi.domain.entities.MovieEntity
import com.aliasadi.domain.usecase.AddMovieToFavorite
import com.aliasadi.domain.usecase.CheckFavoriteStatus
import com.aliasadi.domain.usecase.GetMovieDetails
import com.aliasadi.domain.usecase.RemoveMovieFromFavorite
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoInteractions
import org.mockito.kotlin.whenever

/**
 * Created by Ali Asadi on 16/05/2020
 **/
class MovieDetailsViewModelTest : BaseTest() {

    private val getMovieDetails: GetMovieDetails = mock()
    private val checkFavoriteStatus: CheckFavoriteStatus = mock()
    private val addMovieToFavorite: AddMovieToFavorite = mock()
    private val removeMovieFromFavorite: RemoveMovieFromFavorite = mock()
    private val movieDetailsBundle: MovieDetailsBundle = mock()

    private lateinit var sut: MovieDetailsViewModel

    @Test
    fun `test ui state reflects movie details correctly`() = runUnconfinedTest {
        val movieId = 1413
        val movie = MovieEntity(movieId, "title", "desc", "image", "category", "backgroundUrl")

        createViewModel(
            movieId = movieId,
            movieDetailsResult = Result.success(movie),
            favoriteStatusResult = Result.success(false)
        )

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.title).isEqualTo(movie.title)
            assertThat(emission.description).isEqualTo(movie.description)
            assertThat(emission.imageUrl).isEqualTo(movie.backgroundUrl)
            assertThat(emission.isFavorite).isFalse()
        }

        verify(getMovieDetails).invoke(movieId)
        verify(checkFavoriteStatus).invoke(movieId)
    }

    @Test
    fun `test no change in UI when movie ID is invalid`() = runUnconfinedTest {
        val invalidMovieId = -1
        createViewModel(
            movieId = invalidMovieId,
            movieDetailsResult = Result.failure(mock()),
            favoriteStatusResult = Result.failure(mock())
        )

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(MovieDetailsState())
        }
    }

    @Test
    fun `test movie marked as favorite`() = runUnconfinedTest {
        val movieId = 555
        createViewModel(
            movieId = movieId,
            movieDetailsResult = Result.failure(mock()),
            favoriteStatusResult = Result.success(false)
        )

        sut.onFavoriteClicked()

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isFavorite).isTrue()
        }

        verify(addMovieToFavorite).invoke(movieId)
        verifyNoInteractions(removeMovieFromFavorite)
    }

    @Test
    fun `test movie removed from favorites`() = runUnconfinedTest {
        val movieId = 555
        createViewModel(
            movieId = movieId,
            movieDetailsResult = Result.failure(mock()),
            favoriteStatusResult = Result.success(true)
        )
        sut.onFavoriteClicked()

        sut.uiState.test {
            val emission = awaitItem()
            assertThat(emission.isFavorite).isFalse()
        }

        verify(removeMovieFromFavorite).invoke(movieId)
        verifyNoInteractions(addMovieToFavorite)
    }

    private suspend fun createViewModel(
        movieId: Int,
        movieDetailsResult: Result<MovieEntity>,
        favoriteStatusResult: Result<Boolean>
    ) {
        whenever(movieDetailsBundle.movieId).thenReturn(movieId)
        whenever(getMovieDetails(movieId)).thenReturn(movieDetailsResult)
        whenever(checkFavoriteStatus.invoke(movieId)).thenReturn(favoriteStatusResult)

        sut = MovieDetailsViewModel(
            getMovieDetails = getMovieDetails,
            checkFavoriteStatus = checkFavoriteStatus,
            removeMovieFromFavorite = removeMovieFromFavorite,
            addMovieToFavorite = addMovieToFavorite,
            movieDetailsBundle = movieDetailsBundle
        )
    }
}