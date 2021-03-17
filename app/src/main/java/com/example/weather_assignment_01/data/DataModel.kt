package com.example.weather_assignment_01.data

import com.example.weather_assignment_01.service.WeatherService
import io.reactivex.Single

interface DataModel {
    fun getLocation(query:String): Single<List<Location.Response>>
    fun getWethers(path:String): Single<Weather.WeatherModel>
}
