package com.example.mappoints.repository

import androidx.lifecycle.LiveData
import com.example.mappoints.db.Point
import com.example.mappoints.network.data.GeoResponseX
import retrofit2.Response

interface PointRepository {
    fun getAllPoints(): LiveData<List<Point>>

    fun getPoint(id: Long): LiveData<Point>

    suspend fun insertPoint(point: Point): Long

    suspend fun deletePoint(point: Point)

    suspend fun deletePointById(id: Long)

    suspend fun getAddress(address: String, key:String): Response<GeoResponseX>


}