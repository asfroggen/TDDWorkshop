package com.esaudev.tddworkshop.domain

import com.esaudev.tddworkshop.data.PlaylistService
import com.esaudev.tddworkshop.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistRepository @Inject constructor (
    private val service: PlaylistService
) {

    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> {
        return service.fetchPlaylists()
    }
}