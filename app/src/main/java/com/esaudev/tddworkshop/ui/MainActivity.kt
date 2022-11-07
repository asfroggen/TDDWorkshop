package com.esaudev.tddworkshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.databinding.ActivityMainBinding
import com.esaudev.tddworkshop.ui.list.PlaylistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}