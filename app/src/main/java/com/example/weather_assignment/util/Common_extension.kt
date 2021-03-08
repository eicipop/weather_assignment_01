package com.example.weather_assignment.util

import com.example.weather_assignment.BuildConfig
import com.example.weather_assignment.data.IconAbbreviation

fun IconAbbreviation.getIconUrl() = String.format(BuildConfig.METAWEATHER_ICON, this)
