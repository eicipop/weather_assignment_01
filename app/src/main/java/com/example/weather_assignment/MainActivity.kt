package com.example.weather_assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weather_assignment.data.Weather
import com.example.weather_assignment.service.WeatherService
import com.example.weather_assignment.data.Location
import com.example.weather_assignment.service.WeatherServiceImpl
import com.example.weather_assignment.ui.WeatherAdapter
import com.example.weather_assignment.util.TableItemDecoration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val weatherService by lazy {
        WeatherServiceImpl()
    }

    private lateinit var rvWeather: RecyclerView
    private lateinit var pbLoading: ProgressBar
    private lateinit var tvTest: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var number: Int = 0
    var list = ArrayList<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        rvWeather = findViewById(R.id.rvWeather)
        pbLoading = findViewById(R.id.pbLoading)
        tvTest = findViewById(R.id.tvTest)
        swipeRefreshLayout = findViewById(R.id.swipeLayout)

        loadContents()
    }

    private fun loadContents() {
        pbLoading.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            val weatherList: List<Weather> = weatherService.getWeatherList()
            if (rvWeather.adapter ==null){
                rvWeather.adapter= WeatherAdapter().apply {
                    weathers = weatherList
                }
                getDrawable(R.drawable.decoration_line)?.run{
                    val itemDecoration = TableItemDecoration(rvWeather.context).apply {
                        setDrawable(this@run)
                    }
                    rvWeather.addItemDecoration(itemDecoration)
                }
            }
            pbLoading.visibility = View.GONE
        }, 2000)

        swipeRefreshLayout.setOnRefreshListener {
            number++
            tvTest.text = " Total number = $number"
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }

    private fun loadApi(){

     var retrofit = Retrofit.Builder()
         .baseUrl("https://metaweather.com/")
         .addConverterFactory(GsonConverterFactory.create())
         .build()
     retrofit.create(WeatherService::class.java)

      WeatherService.instance.searchLocation("se")
      WeatherService.instance.getWeatherForDay(123545,2021,3,8)

    }

}