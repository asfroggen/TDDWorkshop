package com.esaudev.tddworkshop.ui.list

import androidx.lifecycle.*
import com.esaudev.tddworkshop.domain.PlaylistRepository
import com.esaudev.tddworkshop.domain.model.Playlist
import com.esaudev.tddworkshop.util.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val repository: PlaylistRepository
        ): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val playlists = MutableLiveData<Result<List<Playlist>>>()

    init {
        loader.postValue(true)

        EspressoIdlingResource.increment()
        viewModelScope.launch {
            repository.getPlaylists().onEach {
                playlists.value = it
                loader.value = false
            }.launchIn(viewModelScope)
        }
    }

    /*val playlists = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }*/

}