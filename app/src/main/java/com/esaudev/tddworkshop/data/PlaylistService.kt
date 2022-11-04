package com.esaudev.tddworkshop.data

import com.esaudev.tddworkshop.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

class PlaylistService {

    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {
        TODO("Not yet implemented")
    }
}