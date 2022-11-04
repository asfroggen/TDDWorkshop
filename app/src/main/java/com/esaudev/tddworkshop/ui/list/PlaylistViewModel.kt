package com.esaudev.tddworkshop.ui.list

import androidx.lifecycle.*
import com.esaudev.tddworkshop.domain.PlaylistRepository
import com.esaudev.tddworkshop.domain.model.Playlist
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlaylistViewModel (
    private val repository: PlaylistRepository
        ): ViewModel() {

    val playlists = MutableLiveData<Result<List<Playlist>>>()

    init {
        viewModelScope.launch {
            repository.getPlaylists().collect {
                playlists.value = it
            }
        }
    }

    /*val playlists = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }*/

}