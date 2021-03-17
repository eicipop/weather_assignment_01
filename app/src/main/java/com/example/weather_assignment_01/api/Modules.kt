package com.example.weather_assignment_01.api

import com.example.weather_assignment_01.BuildConfig
import com.example.weather_assignment_01.data.DataModel
import com.example.weather_assignment_01.data.DataModelImpl
import com.example.weather_assignment_01.service.WeatherService
import com.example.weather_assignment_01.ui.MainViewModel
import com.example.weather_assignment_01.ui.WeatherAdapter
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

var retrofitPart = module {
    single<WeatherService> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.DOMAIN+"api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }
}

var adapterPart = module {
    factory {
        WeatherAdapter()
    }
}

var modelPart = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
}

var viewModelPart = module {
    viewModel {
        MainViewModel(get())
    }
}

var myDiModule = listOf(retrofitPart, adapterPart, modelPart, viewModelPart)