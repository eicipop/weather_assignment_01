package com.example.weather_assignment.service

import com.example.weather_assignment.data.Fixture
import com.example.weather_assignment.data.Location
import com.example.weather_assignment.data.Weather

interface WeatherServiceIm{
    fun getLocation(): Location
    fun getWeather(): Weather
    fun getWeatherList(): List<Weather>
}
class WeatherServiceImpl: WeatherServiceIm {
    override fun getLocation(): Location {
        return Fixture.locations.first()
    }

    override fun getWeather(): Weather {
        return Fixture.seoulWeather
    }

    override fun getWeatherList(): List<Weather> {
        return Fixture.weatherList
    }
}