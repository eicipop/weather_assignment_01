package com.example.weather_assignment_01.data

import androidx.annotation.StringRes
import com.example.weather_assignment_01.util.getIconUrl
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

class Weather {
    data class WeatherModel(
        val weathers: List<ConsolidatedWeather> = mutableListOf(),
        val title: String = ""
    ) {
        fun toList() = listOf(this)
    }

    data class Response(

        val title: String,
        val woeid: Long,
        val consolidatedWeather: List<ConsolidatedWeather>
    )

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

    @JsonClass(generateAdapter = true)
    data class WeatherHeader(
        @StringRes val titleLocal: Int,
        @StringRes val titleToday: Int,
        @StringRes val titleTomorrow: Int
    )
}


enum class IconAbbreviation(abbreviation: String) {
    @field:Json(name = "sn")
    sn("Snow"),

    @field:Json(name = "sl")
    sl("Sleet"),

    @field:Json(name = "h")
    h("Hail"),

    @field:Json(name = "t")
    t("Thunderstorm"),

    @field:Json(name = "hr")
    hr("Heavy"),

    @field:Json(name = "lr")
    lr("Light"),

    @field:Json(name = "s")
    s("Showers"),

    @field:Json(name = "hc")
    hc("Heavy cloud"),

    @field:Json(name = "lc")
    lc("Light cloud"),

    @field:Json(name = "c")
    c("Clear")
}