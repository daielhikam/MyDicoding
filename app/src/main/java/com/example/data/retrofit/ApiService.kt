package com.example.data.retrofit

import com.example.data.response.GetFinishedResponse
import com.example.data.response.GetUpcomingResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

        @GET("events?active=1")
        suspend fun getUpcoming(): Response<GetUpcomingResponse>

        @GET("events?active=0")
        suspend fun getFinished(): Response<GetFinishedResponse>
    }

