package com.esaudev.tddworkshop.details

import com.esaudev.tddworkshop.data.PlaylistDetailService
import com.esaudev.tddworkshop.data.PlaylistDetailsApi
import com.esaudev.tddworkshop.domain.model.PlaylistDetail
import com.esaudev.tddworkshop.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistDetailService: BaseUnitTest() {
    lateinit var service: PlaylistDetailService
    private val id = "100"
    private val api: PlaylistDetailsApi = mock()
    private val playlistDetails: PlaylistDetail = mock()
    private val exception = RuntimeException("Damn backend developers again 500!!!")

    @Test
    fun fetchPlaylistDetailsFromAPI() = runTest {
        mockSuccessfulCase()

        service.fetchPlaylistDetails(id).single()

        verify(api, times(1)).fetchPlaylistDetails(id)
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runTest {
        mockSuccessfulCase()

        assertEquals(Result.success(playlistDetails), service.fetchPlaylistDetails(id).single())
    }

    @Test
    fun emitErrorResultWhenNetworkFails() = runTest {
        mockErrorCase()

        assertEquals("Something went wrong",
            service.fetchPlaylistDetails(id).single().exceptionOrNull()?.message
        )
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchPlaylistDetails(id)).thenThrow(exception)

        service = PlaylistDetailService(api)
    }


    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchPlaylistDetails(id)).thenReturn(playlistDetails)

        service = PlaylistDetailService(api)
    }

}