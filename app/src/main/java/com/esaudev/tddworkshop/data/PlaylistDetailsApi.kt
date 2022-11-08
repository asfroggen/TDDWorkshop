package com.esaudev.tddworkshop.data

import com.esaudev.tddworkshop.domain.model.PlaylistDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaylistDetailsApi {

    @GET("playlistById/{id}")
    suspend fun fetchPlaylistDetails(@Path("id") id: String): PlaylistDetail
}
