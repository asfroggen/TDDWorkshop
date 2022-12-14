package com.esaudev.tddworkshop.ui.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.databinding.ItemPlaylistBinding
import com.esaudev.tddworkshop.domain.model.Playlist

class PlaylistListAdapter(
    private val values: List<Playlist>,
    private val onPlaylistClick: (String) -> Unit
) : RecyclerView.Adapter<PlaylistListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.playlistName.text = item.name
        holder.playlistCategory.text = item.category
        holder.playlistImage.setImageResource(R.drawable.asset_playlist_default)

        holder.playlistContainer.setOnClickListener { onPlaylistClick(item.id) }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        val playlistName: TextView = binding.playlistName
        val playlistCategory: TextView = binding.playlistCategory
        val playlistImage: ImageView = binding.playlistImage
        val playlistContainer: LinearLayout = binding.playlistContainer
    }

}