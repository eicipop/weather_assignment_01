package com.example.weather_assignment.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    @field:Json(name="title")
    val title: String,

    @field:Json(name="location_type")
    val locationType: String,

    @field:Json(name="woeid")
    val woeid: Long,

    @field:Json(name="latt_long")
    val lattLong: String,
)