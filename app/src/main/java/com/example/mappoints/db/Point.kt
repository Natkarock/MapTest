package com.example.mappoints.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Point(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String?,
    val lat: Double,
    val lng: Double,
)