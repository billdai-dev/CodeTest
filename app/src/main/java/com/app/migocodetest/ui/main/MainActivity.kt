package com.app.migocodetest.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import com.app.migocodetest.databinding.ActivityMainBinding
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PassAdapter.Listener {
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainViewModel>()
    private val compositeDisposable = CompositeDisposable()
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

            btnAddPass.clicks()
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribeBy {
                    val duration =
                        etPassDuration.text.toString().toIntOrNull() ?: return@subscribeBy
                    val type =
                        if (rbDayPass.isChecked) PassEntity.PassType.Day
                        else PassEntity.PassType.Hour

                    viewModel.addPass(duration, type)
                }
                .addTo(compositeDisposable)

            validateInput(this)
        }

        viewModel.apiStatus.observe(this) {
            apiInfoAdapter.submitList(listOf(it))
        }

        viewModel.dayPasses.observe(this) {
            dayPassAdapter.submitList(it)
            if (it.isEmpty()) {
                return@observe
            }
            if (!mainAdapter.adapters.contains(dayPassTypeAdapter)) {
                mainAdapter.addAdapter(1, dayPassTypeAdapter)
                mainAdapter.addAdapter(2, dayPassAdapter)
            }
        }

        viewModel.hourPasses.observe(this) {
            hourPassAdapter.submitList(it)
            if (it.isEmpty()) {
                return@observe
            }
            if (!mainAdapter.adapters.contains(hourPassTypeAdapter)) {
                mainAdapter.addAdapter(hourPassTypeAdapter)
                mainAdapter.addAdapter(hourPassAdapter)
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    override fun onActivateBtnClick(pass: PassEntity) {
        //TODO: Activate pass
    }

    private fun validateInput(binding: ActivityMainBinding) {
        with(binding) {
            Observables.combineLatest(
                etPassDuration.textChanges(),
                rgPassType.checkedChanges().skipInitialValue().map { "$it" }
            )
                .map { it.first.isNotBlank() && it.second.isNotBlank() }
                .subscribeBy { btnAddPass.isEnabled = it }
                .addTo(compositeDisposable)
        }
    }
}