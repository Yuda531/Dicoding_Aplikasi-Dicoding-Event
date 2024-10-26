package com.dicoding.aplikasidicodingevent.data.remote.retrofit

import com.dicoding.aplikasidicodingevent.data.remote.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("events/{id}")
    suspend fun getEventDetails(
        @Path("id") id: String
    ): EventResponse

    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int
    ): EventResponse

    @GET("events")
    suspend fun searchEvents(
        @Query("q") keyword: String
    ): EventResponse
}