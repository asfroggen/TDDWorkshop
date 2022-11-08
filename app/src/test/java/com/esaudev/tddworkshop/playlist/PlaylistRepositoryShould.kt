package com.esaudev.tddworkshop.playlist

import com.esaudev.tddworkshop.data.PlaylistService
import com.esaudev.tddworkshop.domain.PlaylistRepository
import com.esaudev.tddworkshop.domain.model.Playlist
import com.esaudev.tddworkshop.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistRepositoryShould: BaseUnitTest() {

    private val service: PlaylistService = mock()
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun `get playlists from service`() = runTest {

        val repository = PlaylistRepository(service)

        repository.getPlaylists()
        advanceUntilIdle()

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun `emit playlist from service`() = runTest {

        val repository = mockSuccessfulCase()

        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun `propagate errors`() = runTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )

        val repository = PlaylistRepository(service)
        return repository
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        val repository = PlaylistRepository(service)
        return repository
    }

}