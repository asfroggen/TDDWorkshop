package com.esaudev.tddworkshop.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esaudev.tddworkshop.R
import com.esaudev.tddworkshop.databinding.ActivityMainBinding
import com.esaudev.tddworkshop.ui.list.PlaylistFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PlaylistFragment.newInstance())
                .commit()
        }
    }
}