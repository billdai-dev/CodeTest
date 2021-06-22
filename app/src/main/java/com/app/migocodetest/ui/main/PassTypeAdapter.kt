package com.app.migocodetest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.migocodetest.R
import com.app.migocodetest.databinding.ItemPassTypeBinding
import com.app.migocodetest.domain.entity.wallet.PassEntity

class PassTypeAdapter(private val type: PassEntity.PassType) :
    RecyclerView.Adapter<PassTypeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPassTypeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.initView()
    }

    override fun getItemCount(): Int = 1

    inner class ViewHolder(binding: ItemPassTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvType: TextView = binding.tvType

        fun initView() {
            val context = tvType.context
            tvType.text = when(type){
                PassEntity.PassType.Day -> context.getString(R.string.wallet_passType_day)
                PassEntity.PassType.Hour -> context.getString(R.string.wallet_passType_hour)
            }
        }
    }
}