package com.example.mappoints.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PointDao {
    @Query("Select *  from Point")
    fun getAll(): LiveData<List<Point>>

    @Query("Select * from Point where id = :id")
    fun getPoint(id: Long): LiveData<Point>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoint(point:Point): Long

    @Insert
    fun insertPoints(alarms: List<Point>)

    @Delete
    suspend fun deletePoint(point: Point)

    @Query("DELETE FROM Point WHERE id = :id")
    suspend fun deleteById(id: Long)

}