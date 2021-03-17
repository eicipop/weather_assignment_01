package com.example.weather_assignment_01.service

import com.example.weather_assignment_01.BuildConfig
import com.example.weather_assignment_01.data.Location
import com.example.weather_assignment_01.data.Weather
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        fun getClient(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.DOMAIN+ "api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit
        }
    }

    @GET("location/search")
    fun searchLocation(@Query("query") txt: String): Single<List<Location.Response>>

    @GET("location/{woeid}")
    fun getWeather(@Path("woeid") woeid: String): Single<Weather.WeatherModel>

    @GET("location/{woeid}")
    fun getWeatherList(@Query("SELECT * FROM woeid") woeid: String): Call<List<Weather.Response>>

    @GET("location/{woeid}/{yyyy}/{mm}/{dd}")
    fun getWeatherForDay(
            @Path("woeid") woeid: Int,
            @Path("yyyy") year: Int,
            @Path("mm") month: Int,
            @Path("dd") day: Int
    ): Call<List<Weather>>
}
