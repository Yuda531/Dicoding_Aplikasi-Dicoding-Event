package com.dicoding.aplikasidicodingevent.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.databinding.FragmentHomeBinding
import com.dicoding.aplikasidicodingevent.ui.event.EventViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventViewModel: EventViewModel
    private lateinit var carouselAdapter: HomeCarouselAdapter
    private lateinit var finishedAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false) // Perbaikan di sini
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        setupCarousel()

        setupFinishedEvents()

        eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        eventViewModel.getUpcomingEvents()
        eventViewModel.upcomingEvents.observe(viewLifecycleOwner) { upcomingEvents ->
            if (upcomingEvents != null) {
                carouselAdapter.setEvents(upcomingEvents)
            }
        }

        eventViewModel.getFinishedEvents()
        eventViewModel.finishedEvents.observe(viewLifecycleOwner) { finishedEvents ->
            if (finishedEvents != null) {
                finishedAdapter.setEvents(finishedEvents)
            }
        }
    }

    private fun setupCarousel() {
        carouselAdapter = HomeCarouselAdapter(listOf()) { event ->
            event.id?.let { eventId ->
                eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    showLoading(isLoading)
                }

                eventViewModel.getEventDetails(eventId.toString())
                val action = HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(eventId)
                findNavController().navigate(action)
            }
        }

        binding.viewPagerUpcomingEvents.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.viewPagerUpcomingEvents.adapter = carouselAdapter
    }


    private fun setupFinishedEvents() {
        finishedAdapter = HomeAdapter(listOf()) { event ->
            event.id?.let { eventId ->
                eventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                    showLoading(isLoading)
                }

                eventViewModel.getEventDetails(eventId.toString())
                val action = HomeFragmentDirections.actionHomeFragmentToEventDetailsFragment(eventId)
                findNavController().navigate(action)
            }
        }
        binding.recyclerViewFinishedEvents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFinishedEvents.adapter = finishedAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

