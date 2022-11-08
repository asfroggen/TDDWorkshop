package com.esaudev.tddworkshop.data

import com.esaudev.tddworkshop.domain.model.PlaylistDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaylistDetailService @Inject constructor(
    private val api: PlaylistDetailsApi
) {

    suspend fun fetchPlaylistDetails(id: String) : Flow<Result<PlaylistDetail>>{
        return flow {
            emit(Result.success(api.fetchPlaylistDetails(id)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}