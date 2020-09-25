package com.example.mappoints.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mappoints.repository.PointRepositoryImpl

class PointListViewModel @ViewModelInject constructor(
    private val pointRepositoryImpl: PointRepositoryImpl,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    val points = pointRepositoryImpl.getAllPoints()

}