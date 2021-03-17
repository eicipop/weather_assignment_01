package com.example.weather_assignment_01.util

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.example.weather_assignment_01.api.myDiModule
import com.example.weather_assignment_01.data.DataModel
import com.example.weather_assignment_01.data.DataModelImpl
import com.example.weather_assignment_01.service.WeatherService

import com.example.weather_assignment_01.ui.MainViewModel
import org.koin.android.ext.android.startKoin

import timber.log.Timber

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startKoin(applicationContext, myDiModule)
    }


    private fun getWeatherService():
            DataModelImpl = DataModelImpl(service = WeatherService.instance)

    fun getMainViewModel(): MainViewModel = MainViewModel(getWeatherService() as DataModel)
}