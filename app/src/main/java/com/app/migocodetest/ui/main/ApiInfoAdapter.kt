package com.app.migocodetest.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.migocodetest.R
import com.app.migocodetest.databinding.ItemApiInfoBinding
import com.app.migocodetest.domain.entity.info.InfoEntity

class ApiInfoAdapter : ListAdapter<InfoEntity, ApiInfoAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemApiInfoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = currentList[position]
        holder.initView(info)
    }

    inner class ViewHolder(binding: ItemApiInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tvInfo: TextView = binding.tvInfo

        fun initView(info: InfoEntity) {
            val context = tvInfo.context
            tvInfo.text = context.getString(R.string.main_apiInfo_text, info.status, info.message)
        }
    }

    private companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<InfoEntity>() {
            override fun areItemsTheSame(oldItem: InfoEntity, newItem: InfoEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: InfoEntity, newItem: InfoEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}