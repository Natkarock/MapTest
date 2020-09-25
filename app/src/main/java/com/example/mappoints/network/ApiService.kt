package com.example.mappoints.network

import com.example.mappoints.network.data.GeoResponseX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("geocode/json")
    suspend fun getAddress(@Query("address")address: String, @Query("key")key: String):Response<GeoResponseX>
}