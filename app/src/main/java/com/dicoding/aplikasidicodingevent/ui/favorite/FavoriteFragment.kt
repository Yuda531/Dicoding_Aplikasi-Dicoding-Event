package com.dicoding.aplikasidicodingevent.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.aplikasidicodingevent.data.Result
import com.dicoding.aplikasidicodingevent.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: FavoriteEventViewModelFactory = FavoriteEventViewModelFactory.getInstance(requireActivity())
        val favoriteEventViewModel: FavoriteEventViewModel by viewModels {
            factory
        }

        val favoriteEventAdapter = FavoriteEventAdapter { favoriteEvent ->
            favoriteEventViewModel.deleteFavoriteEvent(favoriteEvent)

        }

        binding.rvFavoriteEvent.layoutManager = LinearLayoutManager(context)
        binding.rvFavoriteEvent.setHasFixedSize(true)
        binding.rvFavoriteEvent.adapter = favoriteEventAdapter

        favoriteEventViewModel.getAllFavoriteEvent().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> showLoading(true)
                    is Result.Success -> {
                        showLoading(false)
                        val sortedList = result.data.sortedBy { it.id }
                        favoriteEventAdapter.submitList(sortedList)
                    }
                    is Result.Error -> {
                        showLoading(false)
                        showError(result.error)
                    }
                }
            }
        }

        favoriteEventViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.rvFavoriteEvent.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}