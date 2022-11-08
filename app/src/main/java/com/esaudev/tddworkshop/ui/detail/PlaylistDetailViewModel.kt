package com.esaudev.tddworkshop.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.tddworkshop.data.PlaylistDetailService
import com.esaudev.tddworkshop.domain.model.PlaylistDetail
import com.esaudev.tddworkshop.util.EspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val service: PlaylistDetailService,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val playlistDetails = MutableLiveData<Result<PlaylistDetail>>()

    val loader = MutableLiveData<Boolean>()

    init {
        val id = savedStateHandle.get<String>("playlistId")?: ""
        getPlaylistDetails(id)
    }

    fun getPlaylistDetails(id: String) {
        loader.postValue(true)
        EspressoIdlingResource.increment()
        viewModelScope.launch {
            service.fetchPlaylistDetails(id).onEach {
                playlistDetails.value = it
                loader.value = false
            }.launchIn(viewModelScope)
        }
    }

}