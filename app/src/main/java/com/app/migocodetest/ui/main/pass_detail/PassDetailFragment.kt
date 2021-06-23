package com.app.migocodetest.ui.main.pass_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.app.migocodetest.R
import com.app.migocodetest.databinding.FragmentPassDetailBinding
import com.app.migocodetest.domain.entity.wallet.PassEntity
import com.app.migocodetest.ui.main.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class PassDetailFragment : Fragment() {
    private var binding: FragmentPassDetailBinding? = null
    private val activityViewModel by activityViewModels<MainActivityViewModel>()
    private val viewModel by viewModels<PassDetailViewModel>()
    private val sdf: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPassDetailBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.passId.observe(viewLifecycleOwner) { id ->
            val pass =
                activityViewModel.passList.value?.firstOrNull { it.id == id } ?: return@observe
            val passType = pass.type
            val duration = pass.duration
            val currentTs = System.currentTimeMillis()
            val isActivated = pass.activationTimestamp != null
            val isExpired = pass.expirationTimestamp?.run { this < currentTs } ?: false
            val status = when {
                isExpired -> getString(R.string.wallet_pass_expired)
                isActivated -> getString(R.string.wallet_pass_activated)
                else -> getString(R.string.wallet_pass_inactivated)
            }


            binding?.run {
                with(vPass) {
                    tvPassType.text =
                        if (passType == PassEntity.PassType.Day)
                            getString(R.string.wallet_pass_type_day, duration)
                        else
                            getString(R.string.wallet_pass_type_hour, duration)
                    with(btnBuy) {
                        isEnabled = false
                        text = status

                    }
                }

                with(vStatus) {
                    tvKey.text = getString(R.string.passDetail_key_status)
                    tvValue.text = status
                }

                with(vInsertionTime) {
                    val insertionDate = pass.insertionTimestamp?.run { Date(this) }

                    tvKey.text = getString(R.string.passDetail_key_insertionTime)
                    tvValue.text = insertionDate?.run { sdf.format(this) }
                        ?: getString(R.string.passDetail_value_na)
                }

                with(vActivationTime) {
                    val activationDate = pass.activationTimestamp?.run { Date(this) }

                    tvKey.text = getString(R.string.passDetail_key_activationTime)
                    tvValue.text = activationDate?.run { sdf.format(this) }
                        ?: getString(R.string.passDetail_value_na)
                }

                with(vExpirationTime) {
                    val expirationDate = pass.expirationTimestamp?.run { Date(this) }

                    tvKey.text = getString(R.string.passDetail_key_expirationTime)
                    tvValue.text = expirationDate?.run { sdf.format(this) }
                        ?: getString(R.string.passDetail_value_na)
                }
            }

        }
    }

    companion object {
        const val ARG_PASS_ID = "passId"
        fun newInstance(passId: Int) = PassDetailFragment().apply {
            val args = bundleOf(ARG_PASS_ID to passId)
            arguments = args
        }
    }
}