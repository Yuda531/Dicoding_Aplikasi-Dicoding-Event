package com.dicoding.aplikasidicodingevent.ui.event

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.aplikasidicodingevent.data.remote.response.EventResponse
import com.dicoding.aplikasidicodingevent.data.remote.response.ListEventsItem
import com.dicoding.aplikasidicodingevent.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch


class EventViewModel : ViewModel() {

    private val _event = MutableLiveData<EventResponse>()
    val event: LiveData<EventResponse> = _event

    private val _eventDetail = MutableLiveData<ListEventsItem?>()
    val eventDetail: LiveData<ListEventsItem?> = _eventDetail

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listEvent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> = _upcomingEvents

    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> = _finishedEvents

    companion object {
        private const val TAG = "EventViewModel"
    }

    fun getEvents(active: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(active)
                _listEvent.value = response.listEvents
            } catch (e: Exception) {
                Log.e(TAG, "getEvents: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getEventDetails(id: String) {
        if (id.isEmpty()) {
            _eventDetail.value = null
            return
        }
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEventDetails(id)
                _eventDetail.value = response.event
            } catch (e: Exception) {
                Log.e(TAG, "getEventDetails: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUpcomingEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(1)
                _upcomingEvents.value = response.listEvents
            } catch (e: Exception) {
                Log.e(TAG, "getUpcomingEvents: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getFinishedEvents() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getEvents(0)
                _finishedEvents.value = response.listEvents
            } catch (e: Exception) {
                Log.e(TAG, "getFinishedEvents: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
