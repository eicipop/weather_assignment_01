package com.example.weather_assignment.service

import com.example.weather_assignment.BuildConfig
import com.example.weather_assignment.data.Location
import com.example.weather_assignment.data.Weather
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface WeatherService {

    companion object {

        // singleton instance
        val instance: WeatherService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DOMAIN + "api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofit.create(WeatherService::class.java)
        }

        fun getImageUrl(abbr: String): String {
            return BuildConfig.DOMAIN + "static/img/weather/png/" + abbr + ".png"
        }
    }

    @GET("location/search")
    fun searchLocation(@Query("query") txt: String): Call<List<Location>>

    @GET("location/{woeid}/{yyyy}/{mm}/{dd}")
    fun getWeatherForDay(
        @Path("woeid") woeid: Int,
        @Path("yyyy") year: Int,
        @Path("mm") month: Int,
        @Path("dd") day: Int
    ): Call<List<Weather>>
}
