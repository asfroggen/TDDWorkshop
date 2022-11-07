package com.esaudev.tddworkshop

import com.esaudev.tddworkshop.data.PlaylistApi
import com.esaudev.tddworkshop.data.PlaylistService
import com.esaudev.tddworkshop.domain.model.Playlist
import com.esaudev.tddworkshop.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistServiceShould: BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistApi = mock()
    private val playlists: List<Playlist> = mock()

    @Test
    fun `fetch playlists from API`() = runTest {

        service = PlaylistService(api = api)

        service.fetchPlaylists().first()

        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun `convert values to flow result end emits them`() = runTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)

        service = PlaylistService(api)
    }

    @Test
    fun `emits error result when network fails`() = runTest {
        mockFailureCase()

        assertEquals("Something went wrong",
            service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockFailureCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Damn backend developers"))

        service = PlaylistService(api)
    }
}