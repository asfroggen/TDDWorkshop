package com.esaudev.tddworkshop.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.databinding.FragmentPlaylistDetailBinding
import com.esaudev.tddworkshop.databinding.FragmentPlaylistsBinding
import com.esaudev.tddworkshop.util.EspressoIdlingResource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaylistDetailFragment : Fragment() {

    private var _binding : FragmentPlaylistDetailBinding? = null
    private val binding get() =_binding!!

    private val viewModel: PlaylistDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)

        EspressoIdlingResource.increment()
        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.playlistDetails.observe(this as LifecycleOwner) { details ->
            if (details.getOrNull() != null) {
                binding.playListName.text = details.getOrNull()!!.name
                binding.playlistDetails.text = details.getOrNull()!!.details
            } else {
                Snackbar.make(binding.root, R.string.generic_error, Snackbar.LENGTH_SHORT).show()
            }
            EspressoIdlingResource.decrement()
        }

        viewModel.loader.observe(this as LifecycleOwner) { isLoading ->
            binding.detailsLoader.isVisible = isLoading
            EspressoIdlingResource.decrement()
        }
    }

}