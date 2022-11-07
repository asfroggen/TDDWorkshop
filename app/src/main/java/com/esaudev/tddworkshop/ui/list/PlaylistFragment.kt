package com.esaudev.tddworkshop.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.data.PlaylistApi
import com.esaudev.tddworkshop.data.PlaylistService
import com.esaudev.tddworkshop.domain.PlaylistRepository
import com.esaudev.tddworkshop.domain.model.Playlist

class PlaylistFragment : Fragment() {

    lateinit var viewModel: PlaylistViewModel
    lateinit var viewModelFactory: PlaylistViewModelFactory
    private val service = PlaylistService(object : PlaylistApi {})
    private val repository = PlaylistRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlists, container, false)

        setupViewModel()

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null) {
                setupList(view, playlists.getOrNull()!!)
            } else {
                // Handle the null
            }
        }

        return view
    }

    private fun setupList(
        view: View?,
        playlists: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PlaylistListAdapter(playlists)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlaylistFragment().apply {}
    }
}