package com.example.weather_assignment_01.api

import com.example.weather_assignment_01.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient(){
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.DOMAIN+ "api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}
