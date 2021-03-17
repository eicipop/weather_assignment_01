package com.example.weather_assignment_01.model

import com.example.weather_assignment_01.data.Weather
import com.google.gson.annotations.SerializedName

object WeatherModel {
    data class Response(
        @SerializedName("title")
        val title: String,

        @SerializedName("woeid")
        val woeid: Long,

        @SerializedName("consolidatedWeather")
        val consolidatedWeather: List<Weather.ConsolidatedWeather>
    )

    data class ConsolidatedWeather(
        val air_pressure: Double = 0.0,
        val applicable_date: String = "",
        val created: String = "",
        val humidity: Int = 0,
        val id: Long = 0,
        val max_temp: Double = 0.0,
        val min_temp: Double = 0.0,
        val predictability: Int = 0,
        val the_temp: Double = 0.0,
        val visibility: Double = 0.0,
        val weather_state_abbr: String = "",
        val weather_state_name: String = "",
        val wind_direction: Float = 0.0f,
        val wind_direction_compass: String = "",
        val wind_speed: Double = 0.0
    )
}