package com.dicoding.aplikasidicodingevent.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.R
import com.dicoding.aplikasidicodingevent.databinding.FragmentEventBinding


class EventFragment : Fragment() {
    private var _binding: FragmentEventBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View binding is only available between onCreateView and onDestroyView.")

    private lateinit var eventViewModel: EventViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EventAdapter { selectedEvent ->
            selectedEvent.id?.let { eventId ->
                eventViewModel.getEventDetails(eventId.toString())

                val action = if (arguments?.getInt("active") == 1) {
                    R.id.action_eventFragment_to_eventDetailFragment
                } else {
                    R.id.action_eventFinishFragment_to_eventDetailFragment
                }
                val bundle = Bundle().apply {
                    putInt("eventId", eventId)
                }
                findNavController().navigate(action, bundle)
            }
        }

        binding.rvEvents.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvEvents.adapter = adapter

        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]

        eventViewModel.listEvent.observe(viewLifecycleOwner) { events ->
            if (events != null) {
                adapter.submitList(events)
            }
        }

        eventViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val active = arguments?.getInt("active") ?: 1
        eventViewModel.getEvents(active)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
