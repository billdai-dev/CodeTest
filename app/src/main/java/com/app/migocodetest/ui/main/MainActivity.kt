package com.app.migocodetest.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import com.app.migocodetest.databinding.ActivityMainBinding
import com.app.migocodetest.domain.entity.wallet.PassEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PassAdapter.Listener {
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private val apiInfoAdapter by lazy { ApiInfoAdapter() }
    private val dayPassTypeAdapter by lazy { PassTypeAdapter(PassEntity.PassType.Day) }
    private val dayPassAdapter by lazy { PassAdapter(this) }
    private val hourPassTypeAdapter by lazy { PassTypeAdapter(PassEntity.PassType.Hour) }
    private val hourPassAdapter by lazy { PassAdapter(this) }
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

    override fun onBuyBtnClick(pass: PassEntity) {
        //TODO: Buy pass
    }
}