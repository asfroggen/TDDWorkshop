package com.esaudev.tddworkshop.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.data.PlaylistApi
import com.esaudev.tddworkshop.data.PlaylistService
import com.esaudev.tddworkshop.databinding.FragmentPlaylistsBinding
import com.esaudev.tddworkshop.domain.PlaylistRepository
import com.esaudev.tddworkshop.domain.model.Playlist
import com.esaudev.tddworkshop.util.EspressoIdlingResource
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private val viewModel: PlaylistViewModel by viewModels()

    private var _binding : FragmentPlaylistsBinding? = null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        EspressoIdlingResource.increment()

        viewModel.loader.observe(this as LifecycleOwner) { isLoading ->
            binding.loader.isVisible = isLoading
            EspressoIdlingResource.decrement()
        }

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            EspressoIdlingResource.decrement()
            if (playlists.getOrNull() != null) {
                setupList(playlists.getOrNull()!!)
            } else {
                // Handle the null
            }
        }

        return binding.root
    }

    private fun setupList(
        playlists: List<Playlist>
    ) {
        with(binding.playlistList) {
            layoutManager = LinearLayoutManager(context)
            adapter = PlaylistListAdapter(playlists)
        }
    }

    /*private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }*/

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistFragment().apply {}
    }
}