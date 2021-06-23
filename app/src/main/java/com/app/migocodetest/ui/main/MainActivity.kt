package com.app.migocodetest.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.migocodetest.R
import com.app.migocodetest.databinding.ActivityMainBinding
import com.app.migocodetest.ui.main.pass_detail.PassDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainFragment.Listener {
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fcv_main, MainFragment.newInstance())
                .commit()
        }
    }

    override fun onPassClick(passId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, PassDetailFragment.newInstance(passId))
            .addToBackStack(PassDetailFragment::class.java.simpleName)
            .commit()
    }
}