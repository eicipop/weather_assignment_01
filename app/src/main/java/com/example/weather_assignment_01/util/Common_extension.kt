package com.example.weather_assignment_01.util

import com.example.weather_assignment_01.BuildConfig
import com.example.weather_assignment_01.data.IconAbbreviation
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun IconAbbreviation.getIconUrl() = String.format(BuildConfig.METAWEATHER_ICON, this)
fun <T> Maybe<T>.networkThread(): Maybe<T> {
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}