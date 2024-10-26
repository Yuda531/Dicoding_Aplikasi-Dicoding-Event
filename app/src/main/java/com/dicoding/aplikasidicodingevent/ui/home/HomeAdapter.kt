package com.dicoding.aplikasidicodingevent.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.databinding.ItemEventBinding

class HomeAdapter(
    private var events: List<ListEventsItem>,
    private val onClick: (ListEventsItem) -> Unit
) : RecyclerView.Adapter<HomeAdapter.EventViewHolder>() {

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.tvEventName.text = event.name
            binding.tvEventCity.text = event.cityName
            binding.tvEventDate.text = event.beginTime

            Glide.with(binding.root.context)
                .load(event.imageLogo)
                .into(binding.ivEventImage)
            binding.root.setOnClickListener {
                onClick(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    fun setEvents(newEvents: List<ListEventsItem>) {
        val diffCallback = EventDiffCallback(events, newEvents.take(5))
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        events = newEvents.take(5)
        diffResult.dispatchUpdatesTo(this)
    }

    class EventDiffCallback(
        private val oldList: List<ListEventsItem>,
        private val newList: List<ListEventsItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}