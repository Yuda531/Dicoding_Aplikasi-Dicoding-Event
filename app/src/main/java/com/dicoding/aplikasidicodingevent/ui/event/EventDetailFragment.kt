package com.dicoding.aplikasidicodingevent.ui.event

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.dicoding.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.databinding.FragmentEventDetailBinding
import com.dicoding.aplikasidicodingevent.ui.favorite.FavoriteEventViewModel
import com.dicoding.aplikasidicodingevent.ui.favorite.FavoriteEventViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

class EventDetailFragment : Fragment() {
    private lateinit var binding: FragmentEventDetailBinding
    private lateinit var eventViewModel: EventViewModel
    private lateinit var favoriteEventViewModel: FavoriteEventViewModel
    private var eventId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        eventViewModel.eventDetail.observe(viewLifecycleOwner) { event ->
            if (event != null) {
                displayEventDetails(event)
            }
        }

        eventId = arguments?.getInt("eventId")

        eventId?.let { id ->
            eventViewModel.getEventDetails(id.toString())
        }

        eventViewModel.eventDetail.observe(viewLifecycleOwner) { event ->
            if (event != null) {
                displayEventDetails(event)
            }
        }

        val factory = FavoriteEventViewModelFactory.getInstance(requireContext())
        favoriteEventViewModel =
            ViewModelProvider(this, factory).get(FavoriteEventViewModel::class.java)

        var isFavorite = false
        binding.btnFavorite.setOnClickListener {
            eventViewModel.eventDetail.value?.let { event ->
                if (isFavorite) {
                    favoriteEventViewModel.deleteFavoriteEvent(
                        FavoriteEvent(
                            id = event.id.toString(),
                            name = event.name.toString(),
                            mediaCover = event.imageLogo.toString()
                        )
                    )
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                } else {
                    favoriteEventViewModel.insertFavoriteEvent(
                        FavoriteEvent(
                            id = event.id.toString(),
                            name = event.name.toString(),
                            mediaCover = event.imageLogo.toString()
                        )
                    )
                    binding.btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
                }
                isFavorite = !isFavorite
            }
        }

        eventViewModel.eventDetail.observe(viewLifecycleOwner) { event ->
            if (event != null) {
                displayEventDetails(event)
                favoriteEventViewModel.getFavoriteEventById(event.id.toString())
                    .observe(viewLifecycleOwner) { favoriteEvent ->
                        if (favoriteEvent != null) {
                            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_filled)
                            isFavorite = true
                        } else {
                            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_24)
                            isFavorite = false
                        }
                    }
            }
        }

    }

    private fun displayEventDetails(event: ListEventsItem) {
        binding.apply {
            tvEventName.text = event.name
            tvEventSummary.text = event.summary
            tvEventOwner.text = getString(R.string.owner_event, event.ownerName)
            tvEventCity.text = getString(R.string.place_events, event.cityName)

            val timeEventFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val dateEventFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            val eventDate = event.beginTime?.let { timeEventFormat.parse(it) }
            if (eventDate != null) {
                tvEventDate.text =
                    getString(R.string.date_events, dateEventFormat.format(eventDate))
                tvEventTime.text = getString(R.string.time_event, timeFormat.format(eventDate))
            }

            val remainingQuota = event.quota?.minus(event.registrants ?: 0) ?: 0
            tvEventQuota.text = getString(R.string.quota_event, remainingQuota)
            tvEventDescription.text = event.description?.let {
                HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }

            Glide.with(this@EventDetailFragment)
                .load(event.imageLogo)
                .into(binding.ivEventImage)

            btnSeeDetails.setOnClickListener {
                Toast.makeText(requireContext(), "Opening Link", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                startActivity(intent)
        }
        }
    }
}