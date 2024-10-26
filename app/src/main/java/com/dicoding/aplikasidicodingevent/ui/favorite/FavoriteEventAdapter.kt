package com.dicoding.aplikasidicodingevent.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.dicoding.aplikasidicodingevent.databinding.ItemFavoriteBinding

class FavoriteEventAdapter(private val onFavoriteClick: (FavoriteEvent) -> Unit) :
    ListAdapter<FavoriteEvent, FavoriteEventAdapter.FavoriteEventViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteEventViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteEventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteEventViewHolder, position: Int) {
        val favoriteEvent = getItem(position)
        holder.bind(favoriteEvent, onFavoriteClick)
    }

    inner class FavoriteEventViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteEvent: FavoriteEvent, onFavoriteClick: (FavoriteEvent) -> Unit) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(favoriteEvent.mediaCover)
                    .into(imgPoster)
                tvItemTitle.text = favoriteEvent.name

                btnFavorite.setOnClickListener {
                    onFavoriteClick(favoriteEvent)
                }

                itemView.setOnClickListener { view ->
                    val toEventDetailFragment = FavoriteFragmentDirections.actionFavoriteFragmentToEventDetailFragment(favoriteEvent.id.toInt())
                    view.findNavController().navigate(toEventDetailFragment)
                }

            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteEvent>() {
            override fun areItemsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: FavoriteEvent, newItem: FavoriteEvent): Boolean {
                return oldItem == newItem
            }
        }
    }
}