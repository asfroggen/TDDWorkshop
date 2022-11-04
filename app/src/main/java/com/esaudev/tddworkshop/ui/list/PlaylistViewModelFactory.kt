package com.esaudev.tddworkshop.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esaudev.tddworkshop.domain.PlaylistRepository

class PlaylistViewModelFactory(
    private val repository: PlaylistRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistViewModel(repository) as T
    }
}
