package com.esaudev.tddworkshop.data

import com.esaudev.tddworkshop.domain.model.Playlist

interface PlaylistApi {

    suspend fun fetchAllPlaylists(): List<Playlist> {
        TODO("Not yet implemented")
    }
}