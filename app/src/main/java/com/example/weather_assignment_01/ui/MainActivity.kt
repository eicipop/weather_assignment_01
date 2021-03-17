package com.example.weather_assignment_01.ui

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weather_assignment_01.R
import com.example.weather_assignment_01.databinding.ActivityMainBinding
import com.example.weather_assignment_01.ui.base.BaseActivity
import com.example.weather_assignment_01.util.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.weather_assignment_01.util.WeatherApplication
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainActivity : BaseActivity() {
    private val mainAdapter: WeatherAdapter by inject()

    private val mainViewModel by lazy {
        WeatherApplication().getMainViewModel()
    }

    lateinit var binding: ActivityMainBinding
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var number: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            vm = mainViewModel
            lifecycleOwner = this@MainActivity
        }
        mainViewModel.weatherList.observe(
            this,
            Observer { BindingAdapter.setWeatherAdapter(binding.rvWeather, it) })
        mainViewModel.getWeatherList()

        //mainViewModel.init()
    }
}


