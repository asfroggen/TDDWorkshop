package com.esaudev.tddworkshop.domain.model

import androidx.annotation.DrawableRes
import com.esaudev.tddworkshop.R

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    @DrawableRes val image: Int = R.drawable.asset_playlist_default
)