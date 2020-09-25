package com.example.mappoints.utils

import java.lang.Exception
import java.lang.IndexOutOfBoundsException

object Validation {
    private fun toDouble(text:String):Double {
        try {
            return  text.toDouble()
        }catch (e: Exception){
            throw (e)
        }
    }

    private fun replaceToDot(text: String): String{
        return  text.replace(',','.')
    }

    fun validateCoordinate(lat: String, lng: String): Pair<Double, Double>{
        try {
          val commasLat = replaceToDot(lat)
          val commasLon = replaceToDot(lng)
          val formatLat = toDouble(commasLat)
          val formatLong = toDouble(commasLon)
          if((-90f..90f).contains(formatLat) && (-120f..120f).contains(formatLong) ){
            return Pair(formatLat, formatLong)
            }else{
              throw IndexOutOfBoundsException()
          }
        }catch (e: Exception){
            throw  e
        }
    }

    private  const val LOG_TAG = "Validation_LOG"

}