package com.dicoding.aplikasidicodingevent.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.aplikasidicodingevent.data.FavoriteEventRepository
import com.dicoding.aplikasidicodingevent.data.Result
import com.dicoding.aplikasidicodingevent.data.local.entity.FavoriteEvent

class FavoriteEventViewModel(private val favoriteEventRepository: FavoriteEventRepository) :
    ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllFavoriteEvent(): LiveData<Result<List<FavoriteEvent>>> =
        favoriteEventRepository.getAllFavoriteEvent()

    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        favoriteEventRepository.insertFavoriteEvent(favoriteEvent)
    }

    fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent) {
        favoriteEventRepository.deleteFavoriteEvent(favoriteEvent)
    }

    fun updateFavoriteEvent(favoriteEvent: FavoriteEvent) {
        favoriteEventRepository.updateFavoriteEvent(favoriteEvent)
    }

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
        return favoriteEventRepository.getFavoriteEventById(id)
    }
}