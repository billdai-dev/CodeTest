package com.app.migocodetest.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.app.migocodetest.databinding.FragmentMainBinding
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
class MainFragment : Fragment(), PassAdapter.Listener {
    private var binding: FragmentMainBinding? = null
    private var listener: Listener? = null
    private val viewModel by activityViewModels<MainActivityViewModel>()
    private val compositeDisposable = CompositeDisposable()
    private val apiInfoAdapter by lazy { ApiInfoAdapter() }
    private val dayPassTypeAdapter by lazy { PassTypeAdapter(PassEntity.PassType.Day) }
    private val dayPassAdapter by lazy { PassAdapter(this) }
    private val hourPassTypeAdapter by lazy { PassTypeAdapter(PassEntity.PassType.Hour) }
    private val hourPassAdapter by lazy { PassAdapter(this) }
    private val mainAdapter by lazy { ConcatAdapter(apiInfoAdapter) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? Listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
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
                    ViewCompat.getWindowInsetsController(root)?.hide(WindowInsetsCompat.Type.ime())
                }
                .addTo(compositeDisposable)

            validateInput(this)

        }

        viewModel.apiStatus.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                apiInfoAdapter.submitList(null)
            } else {
                apiInfoAdapter.submitList(listOf(it))
            }
        }

        viewModel.dayPasses.observe(viewLifecycleOwner) {
            dayPassAdapter.submitList(it)
            if (it.isEmpty()) {
                return@observe
            }
            if (!mainAdapter.adapters.contains(dayPassTypeAdapter)) {
                mainAdapter.addAdapter(1, dayPassTypeAdapter)
                mainAdapter.addAdapter(2, dayPassAdapter)
            }
        }

        viewModel.hourPasses.observe(viewLifecycleOwner) {
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

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }

    override fun onPassClick(passId: Int) {
        listener?.onPassClick(passId)
    }

    override fun onActivateBtnClick(pass: PassEntity) {
        viewModel.activatePass(pass)
    }

    private fun validateInput(binding: FragmentMainBinding) {
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

    interface Listener {
        fun onPassClick(passId: Int)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}