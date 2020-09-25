package com.example.mappoints.network

import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.net.ConnectException

object BaseApiCall {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String,
    ): ApiResult<T>? {
        return safeApiResult(call, errorMessage)
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String,
    ): ApiResult<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful && response.body() !== null) return ApiResult.Success(response.body()!!)
            return ApiResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        } catch (e: ConnectException) {
            return ApiResult.Error(ConnectException("Connect error - $errorMessage"))
        } catch (e: IOException) {
            return ApiResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        }catch (e: Exception){
            return ApiResult.Error(IOException("Error Occurred during getting safe Api result, Custom ERROR - $errorMessage"))
        }

    }
}