package com.dicoding.aplikasidicodingevent.di

import android.content.Context
import com.dicoding.aplikasidicodingevent.data.FavoriteEventRepository
import com.dicoding.aplikasidicodingevent.data.local.room.FavoriteEventDatabase
import com.dicoding.aplikasidicodingevent.data.remote.retrofit.ApiConfig
import com.dicoding.aplikasidicodingevent.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteEventRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteEventDatabase.getInstance(context)
        val dao = database.favoriteEventDao()
        val appExecutors = AppExecutors()
        return FavoriteEventRepository.getInstance(apiService, dao, appExecutors)
    }
}