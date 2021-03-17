package com.example.weather_assignment_01.data

import com.example.weather_assignment_01.util.getIconUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
class Location {

    data class Response(
        @field:Json(name = "title")
        val title: String,

        @field:Json(name = "location_type")
        val location_type: String,

        @field:Json(name = "woeid")
        val woeid: Long,

        @field:Json(name = "latt_long")
        val latt_long: String,
    ) : Serializable

    @JsonClass(generateAdapter = true)
    data class ConsolidatedWeather(
        @field:Json(name = "weather_state_name")
        val weatherStateName: String,

        @field:Json(name = "weather_state_abbr")
        val weatherStateAbbr: IconAbbreviation,

        @field:Json(name = "the_temp")
        val theTemp: Double,

        @field:Json(name = "humidity")
        val humidity: Int,

        @field:Json(name = "applicable_date")
        val applicableDate: String
    ) {
        val iconUrl = weatherStateAbbr.getIconUrl()
    }

}