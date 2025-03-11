package com.example.data.retrofit

import com.example.data.response.getFinishedResponse
import com.example.data.response.getUpcomingResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

        @GET("events?active=1") // API untuk upcoming events
        suspend fun getUpcoming(): Response<getUpcomingResponse>

        @GET("events?active=0") // API untuk finished events
        suspend fun getFinished(): Response<getFinishedResponse>
    }

