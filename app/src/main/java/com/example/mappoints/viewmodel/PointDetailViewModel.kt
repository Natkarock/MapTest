package com.example.mappoints.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mappoints.db.Point
import com.example.mappoints.repository.PointRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PointDetailViewModel @ViewModelInject constructor(
    private val pointRepositoryImpl: PointRepositoryImpl,
    @Assisted savedStateHandle: SavedStateHandle
):ViewModel() {
    fun getPoint(id:Long) = pointRepositoryImpl.getPoint(id)

    fun insertPoint(point:Point){
        viewModelScope.launch(Dispatchers.IO) {
            pointRepositoryImpl.insertPoint(point)
        }
    }

    fun deletePoint(point: Point){
        viewModelScope.launch(Dispatchers.IO) {
            pointRepositoryImpl.deletePoint(point)
        }
    }

    fun deletePointById(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            pointRepositoryImpl.deletePointById(id)
        }
    }


}