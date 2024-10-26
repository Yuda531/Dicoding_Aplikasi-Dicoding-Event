package com.dicoding.aplikasidicodingevent.ui.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.aplikasidicodingevent.data.FavoriteEventRepository
import com.dicoding.aplikasidicodingevent.di.Injection

class FavoriteEventViewModelFactory private constructor(private val favoriteEventRepository: FavoriteEventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteEventViewModel::class.java)) {
            return FavoriteEventViewModel(favoriteEventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object{
        @Volatile
        private var instance: FavoriteEventViewModelFactory? = null
        fun getInstance(context: Context): FavoriteEventViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteEventViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}