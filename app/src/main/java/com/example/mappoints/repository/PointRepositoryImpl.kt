package com.example.mappoints.repository

import androidx.lifecycle.LiveData
import com.example.mappoints.db.Point
import com.example.mappoints.db.PointDao
import com.example.mappoints.network.ApiService
import com.example.mappoints.network.data.GeoResponseX
import retrofit2.Response
import javax.inject.Inject

class PointRepositoryImpl @Inject constructor(private val pointDao: PointDao, private  val apiService: ApiService)  : PointRepository {


    override fun getAllPoints(): LiveData<List<Point>> {
        return  pointDao.getAll()
    }

    override fun getPoint(id: Long): LiveData<Point> {
        return pointDao.getPoint(id)
    }

    override suspend fun insertPoint(point: Point): Long {
        return pointDao.insertPoint(point)
    }

    override suspend  fun deletePoint(point: Point){
      pointDao.deletePoint(point)
    }

    override suspend fun deletePointById(id: Long) {
        pointDao.deleteById(id)
    }

    override suspend fun getAddress(address: String, key: String): Response<GeoResponseX> {
       return  apiService.getAddress(address, key)
    }
}