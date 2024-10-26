package com.dicoding.aplikasidicodingevent.ui.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.aplikasidicodingevent.databinding.ItemSettingBinding

class SettingsAdapter(private val settingsList: List<SettingItem>) : RecyclerView.Adapter<SettingsAdapter.SettingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val binding = ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SettingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        val settingItem = settingsList[position]
        holder.bind(settingItem)
    }

    override fun getItemCount() = settingsList.size

    inner class SettingViewHolder(private val binding: ItemSettingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(settingItem: SettingItem) {
            binding.apply {
                tvSettingTitle.text = settingItem.title
                tvSettingDescription.text = settingItem.description
                switchSetting.isChecked = settingItem.isEnabled
                switchSetting.setOnCheckedChangeListener { _, isChecked ->
                    settingItem.isEnabled = isChecked
                }
            }
        }
    }
}

data class SettingItem(val title: String, val description: String, var isEnabled: Boolean)
