package com.esaudev.tddworkshop.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.databinding.FragmentPlaylistDetailBinding
import com.esaudev.tddworkshop.databinding.FragmentPlaylistsBinding

class PlaylistDetailFragment : Fragment() {

    private var _binding : FragmentPlaylistDetailBinding? = null
    private val binding get() =_binding!!

    private val args: PlaylistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlaylistDetailBinding.inflate(inflater, container, false)

        binding.test.text = args.playlistId

        return binding.root
    }

}