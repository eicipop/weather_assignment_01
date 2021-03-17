package com.example.weather_assignment_01.api

import android.content.Context
import com.example.weather_assignment_01.BuildConfig
import com.example.weather_assignment_01.service.WeatherService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.net.CookiePolicy
import java.util.concurrent.TimeUnit

class ApiTask {
    companion object{
        private var CONNECT_TIMEOUT = 5000L
        private var WRITE_TIMEOUT = 5000L
        private var READ_TIMEOUT = 5000L
        private var client : OkHttpClient? = null
        private var Interface: WeatherService? = null

        fun setTimeOut(context: Context?, connectTimeout: Long, writeTimeOut: Long, readTimeOut: Long): WeatherService? {
            Interface = null

            CONNECT_TIMEOUT = connectTimeout
            WRITE_TIMEOUT = writeTimeOut
            READ_TIMEOUT = readTimeOut

            return getInstance(context)
        }
        fun getInstance(context: Context?) : WeatherService{
            if(Interface == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                val cookieManager = cookie()
                //val requestInterceptor = PMRequestInterceptor(params)
               // val responseInterceptor = PMResponseInterceptor(context)

                //OkHttpClient 생성

                val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                    .cookieJar(JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                    .addInterceptor(httpLoggingInterceptor) //http 로그 확인


                client = builder.build()

                val gson = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                    .create()

//                val mapper = ObjectMapper()
//                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//                mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
//                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false)
//                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)

                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.DOMAIN + "/api")
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson)) //Json Parser 추가
                    .build()

                Interface = retrofit.create(WeatherService::class.java)
            }

            return Interface!!
        }

        private fun cookie() : CookieManager {
            //쿠키 메니저의 cookie policy를 변경 합니다.
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)

            return cookieManager
        }

    }
}