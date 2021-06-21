package com.app.migocodetest.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import com.app.migocodetest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private val apiInfoAdapter by lazy { ApiInfoAdapter() }
    private val mainAdapter by lazy { ConcatAdapter(apiInfoAdapter) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)

            rvMain.adapter = mainAdapter
        }
        viewModel.apiStatus.observe(this) {
            apiInfoAdapter.submitList(listOf(it))
        }
    }
}