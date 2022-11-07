package com.esaudev.tddworkshop

import com.esaudev.tddworkshop.domain.PlaylistRepository
import com.esaudev.tddworkshop.domain.model.Playlist
import com.esaudev.tddworkshop.ui.list.PlaylistViewModel
import com.esaudev.tddworkshop.utils.BaseUnitTest
import com.esaudev.tddworkshop.utils.captureValues
import com.esaudev.tddworkshop.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistViewModelShould: BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expectedException = RuntimeException("Something went wrong")
    private val expectedSuccess = Result.success(playlists)
    private val expectedFailure = Result.failure<List<Playlist>>(exception = expectedException)


    @Test
    fun `get playlists from repository`() = runTest {

        val viewModel = mockSuccessfulCase()
        viewModel.playlists.getValueForTest()
        advanceUntilIdle()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun `emit playlist from repository`() = runTest {

        val viewModel = mockSuccessfulCase()
        advanceUntilIdle()

        assertEquals(expectedSuccess, viewModel.playlists.getValueForTest())
    }

    @Test
    fun `emit error when receive error`() = runTest {
        val viewModel = mockFailureCase()
        advanceUntilIdle()
        assertEquals(expectedException, viewModel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun `show loader while loading the playlists`() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(true, values.first())
        }
    }

    @Test
    fun `close loader when playlist are ready`() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            advanceUntilIdle()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun `close loader after network error`() = runTest {
        val viewModel = mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.playlists.getValueForTest()
            advanceUntilIdle()
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockSuccessfulCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expectedSuccess)
                }
            )
        }

        return PlaylistViewModel(repository)
    }

    private suspend fun mockFailureCase(): PlaylistViewModel {
        runBlocking {
            whenever(repository.getPlaylists()).thenReturn(
                flow {
                    emit(expectedFailure)
                }
            )
        }

        return PlaylistViewModel(repository)
    }
}