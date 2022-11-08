package com.esaudev.tddworkshop.details

import com.esaudev.tddworkshop.data.PlaylistDetailService
import com.esaudev.tddworkshop.domain.model.Playlist
import com.esaudev.tddworkshop.domain.model.PlaylistDetail
import com.esaudev.tddworkshop.ui.detail.PlaylistDetailViewModel
import com.esaudev.tddworkshop.utils.BaseUnitTest
import com.esaudev.tddworkshop.utils.captureValues
import com.esaudev.tddworkshop.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistDetailViewModelShould: BaseUnitTest() {

    lateinit var viewModel: PlaylistDetailViewModel
    private val id = "1"
    private val service: PlaylistDetailService = mock()
    private val playlistDetails: PlaylistDetail = mock()

    private val expected = Result.success(playlistDetails)
    private val error = Result.failure<PlaylistDetail>(RuntimeException(""))

    @Test
    fun `get playlist details from service`() = runTest {
        viewModel = PlaylistDetailViewModel(service)
        viewModel.getPlaylistDetails(id)

        advanceUntilIdle()
        viewModel.playlistDetails.getValueForTest()


        verify(service, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun `emit playlist details from service`() = runTest {
        mockSuccessfulCase()
        viewModel.getPlaylistDetails(id)
        advanceUntilIdle()
        assertEquals(expected, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun `emit error when service fails`() = runTest {
        mockFailureCase()
        viewModel.getPlaylistDetails(id)
        advanceUntilIdle()
        assertEquals(error, viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun `show loader while loading the playlists`() = runTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            advanceUntilIdle()
            assertEquals(true, values.first())
        }
    }

    @Test
    fun `close loader when playlist are ready`() = runTest {
        mockSuccessfulCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            advanceUntilIdle()
            assertEquals(false, values.last())
        }
    }

    @Test
    fun `close loader after network error`() = runTest {
        mockFailureCase()

        viewModel.loader.captureValues {
            viewModel.getPlaylistDetails(id)
            viewModel.playlistDetails.getValueForTest()
            advanceUntilIdle()
            assertEquals(false, values.last())
        }
    }

    private suspend fun mockFailureCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(flow { emit(error) })
        viewModel = PlaylistDetailViewModel(service)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(service.fetchPlaylistDetails(id)).thenReturn(flow { emit(expected) })

        viewModel = PlaylistDetailViewModel(service)
    }

}