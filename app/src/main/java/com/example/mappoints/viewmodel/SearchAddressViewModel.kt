package com.example.mappoints.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mappoints.db.Point
import com.example.mappoints.network.ApiResult
import com.example.mappoints.network.BaseApiCall
import com.example.mappoints.repository.PointRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchAddressViewModel @ViewModelInject constructor(
    private val pointRepositoryImpl: PointRepositoryImpl,
    @Assisted savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isSuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    val errorMessage: MutableLiveData<String?> = MutableLiveData(null)


    fun getAddress(address: String, key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(true)
            errorMessage.postValue(null)
            val result = BaseApiCall.safeApiCall({ pointRepositoryImpl.getAddress(address, key) },
                errorMessage = "Error")
            when (result) {
                is ApiResult.Success -> {
                    val data = result.data
                    if (data.status == "OK") {
                        if (data.results != null && data.results.size > 0) {
                            val firstResult = data.results[0]
                            val location = firstResult.geometry.location
                            val point = Point(name = firstResult.formatted_address,
                                lat = location.lat,
                                lng = location.lng)
                            insertPoint(point)
                            isSuccess.postValue(true)
                        }
                    }else if (data.status ==  STATUS_ZERO) {
                        errorMessage.postValue(STATUS_ZERO)
                    }else {
                        errorMessage.postValue(STATUS_ERROR)
                    }
                }
                is ApiResult.Error -> {
                    errorMessage.postValue(STATUS_ERROR)
                }
                else -> {
                    errorMessage.postValue(STATUS_ERROR)
                }
            }
            loading.postValue(false)
        }
    }

    fun insertPoint(point:Point){
        viewModelScope.launch(Dispatchers.IO) {
            pointRepositoryImpl.insertPoint(point)
        }
    }

    companion object{
        const val STATUS_ERROR = "STATUS_ERROR"
        const val STATUS_ZERO = "ZERO_RESULTS"

    }
}