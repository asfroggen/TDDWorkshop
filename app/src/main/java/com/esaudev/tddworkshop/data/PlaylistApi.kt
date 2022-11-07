package com.esaudev.tddworkshop.data

import com.esaudev.tddworkshop.domain.model.Playlist
import retrofit2.http.GET

interface PlaylistApi {

    @GET("/playlist")
    suspend fun fetchAllPlaylists(): List<Playlist>
}