package com.example.weather_assignment_01.data

import com.example.weather_assignment_01.service.WeatherService
import io.reactivex.Single

class DataModelImpl (private val service:WeatherService):DataModel{
    override fun getLocation(query: String): Single<List<Location.Response>> {
        return service.searchLocation(txt= query)
    }

    override fun getWethers(path: String): Single<Weather.WeatherModel> {
        return service.getWeather(woeid = path)
    }

}