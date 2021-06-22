package com.app.migocodetest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.migocodetest.R
import com.app.migocodetest.databinding.ItemPassBinding
import com.app.migocodetest.domain.entity.wallet.PassEntity

class PassAdapter(private val listener: Listener) :
    ListAdapter<PassEntity, PassAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPassBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = currentList[position]
        holder.initView(info)
    }

    inner class ViewHolder(binding: ItemPassBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvType: TextView = binding.tvPassType
        private val btnActivate: Button = binding.btnBuy

        fun initView(pass: PassEntity) {
            val context = tvType.context
            val duration = pass.duration
            tvType.text = when (pass.type) {
                PassEntity.PassType.Day -> context.getString(
                    R.string.wallet_pass_type_day,
                    duration
                )
                PassEntity.PassType.Hour -> context.getString(
                    R.string.wallet_pass_type_hour,
                    duration
                )
                else -> ""
            }

            with(btnActivate) {
                val isActivated = pass.activationTimestamp != null
                val expirationTs = pass.expirationTimestamp
                val isExpired =
                    expirationTs != null && expirationTs < System.currentTimeMillis() / 1000
                when {
                    isExpired -> {
                        isEnabled = false
                        text = context.getString(R.string.wallet_pass_expired)
                    }
                    isActivated -> {
                        isEnabled = false
                        text = context.getString(R.string.wallet_pass_activated)
                    }
                    else -> {
                        isEnabled = true
                        text = context.getString(R.string.wallet_pass_activate)
                    }
                }

                setOnClickListener {
                    listener.onActivateBtnClick(pass)
                }
            }
        }
    }

    interface Listener {
        fun onActivateBtnClick(pass: PassEntity)
    }

    private companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PassEntity>() {
            override fun areItemsTheSame(oldItem: PassEntity, newItem: PassEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PassEntity, newItem: PassEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}