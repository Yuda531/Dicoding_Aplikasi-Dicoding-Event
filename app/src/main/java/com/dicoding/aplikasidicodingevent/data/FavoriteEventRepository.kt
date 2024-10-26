package com.dicoding.aplikasidicodingevent.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.dicoding.aplikasidicodingevent.data.local.entity.FavoriteEvent
import com.dicoding.aplikasidicodingevent.data.local.room.FavoriteEventDao
import com.dicoding.aplikasidicodingevent.data.remote.retrofit.ApiService
import com.dicoding.aplikasidicodingevent.utils.AppExecutors

class FavoriteEventRepository(
    private val apiService: ApiService,
    private val favoriteEventDao: FavoriteEventDao,
    private val appExecutors: AppExecutors
) {
    fun getAllFavoriteEvent(): LiveData<Result<List<FavoriteEvent>>> {
        return liveData {
            emit(Result.Loading)
            try {
                val response: LiveData<List<FavoriteEvent>> = favoriteEventDao.getAllFavoriteEvent()
                emitSource(response.map { Result.Success(it) })
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun insertFavoriteEvent(favoriteEvent: FavoriteEvent) {
        appExecutors.diskIO.execute {
            favoriteEventDao.insert(favoriteEvent)
        }
    }
    fun updateFavoriteEvent(favoriteEvent: FavoriteEvent) {
        appExecutors.diskIO.execute {
            favoriteEventDao.update(favoriteEvent)
        }
    }

    fun deleteFavoriteEvent(favoriteEvent: FavoriteEvent) {
        appExecutors.diskIO.execute {
            favoriteEventDao.delete(favoriteEvent)
        }
    }

    fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
        return favoriteEventDao.getFavoriteEventById(id)
    }

    companion object {
        @Volatile
        private var instance: FavoriteEventRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteEventDao: FavoriteEventDao,
            appExecutors: AppExecutors
        ): FavoriteEventRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteEventRepository(apiService, favoriteEventDao, appExecutors)
            }.also { instance = it }
    }
}