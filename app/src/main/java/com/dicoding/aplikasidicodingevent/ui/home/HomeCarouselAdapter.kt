package com.dicoding.aplikasidicodingevent.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.databinding.ItemCarouselBinding

class HomeCarouselAdapter(
    private var events: List<ListEventsItem>,
    private val onClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<HomeCarouselAdapter.CarouselViewHolder>() {

    inner class CarouselViewHolder(private val binding: ItemCarouselBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name
            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.ivEventImage)
            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val binding = ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun setEvents(events: List<ListEventsItem>) {
        this.events = events.take(5)
        notifyDataSetChanged()
    }
}
