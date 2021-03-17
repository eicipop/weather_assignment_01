package com.example.weather_assignment_01.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_assignment_01.data.DataModel
import com.example.weather_assignment_01.data.Location
import com.example.weather_assignment_01.data.Weather
import com.example.weather_assignment_01.service.WeatherService
import com.example.weather_assignment_01.ui.base.BaseViewModel
import com.example.weather_assignment_01.util.networkThread
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(private val model: DataModel): BaseViewModel() {

    val progressVisibility = ObservableBoolean(true)
    val isRefreshing = ObservableBoolean(false)

    private val disposable = CompositeDisposable()
    private val _weatherList: MutableLiveData<List<Weather.WeatherModel>> = MutableLiveData()
    val weatherList: MutableLiveData<List<Weather.WeatherModel>>
        get() = _weatherList

    private val TAG = "MainViewModel"
    private val _locations: MutableLiveData<List<Location.Response>> = MutableLiveData()
    val locations: MutableLiveData<List<Location.Response>>
        get() = _locations

    fun init() {
        locationSearch()
            .getWeatherList()
            .reduce { t1: List<Weather.WeatherModel>, t2: List<Weather.WeatherModel> -> t1 + t2 }
            .networkThread()
            .subscribe({ result ->
                progressVisibility.set(false)
                isRefreshing.set(false)

            }, { e ->
                Log.e("tag", e.toString())
            })
            .addTo(disposable)
    }

    private fun locationSearch(): Observable<Location.Response> {
        return WeatherService.instance
            .searchLocation("se")
            .toObservable()
            .flatMapIterable { it }
    }

    private fun Observable<Location.Response>.getWeatherList(): Observable<List<Weather.WeatherModel>> {
        return this.flatMapSingle { location ->
            WeatherService.instance.getWeather(location.woeid.toString())
                .map {
                    Weather.WeatherModel(weathers = it.weathers.take(2), title = location.title)
                        .toList()
                }
        }
    }


    @SuppressLint("LogNotTimber")
    fun getLocationSearch(query: String) {
        addDisposable(model.getLocation("se")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("loading start")
                showLoading()
                it.run {
                    Log.d(TAG, "locations : $locations")
                    _locations.postValue(this)
                }
                hideLoading()
            }, {
                Log.d(TAG, "response error, message : ${it.message}")
            })
        )
    }

    @SuppressLint("LogNotTimber")
    fun getWeatherList() {
        addDisposable(model.getWethers("${_locations.value}")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("loading start")
                showLoading()
                it.run {
                    Log.d(TAG, "weathers : $weathers")
                    _weatherList.postValue(listOf(this))
                }
                hideLoading()
            }, {
                Log.d(TAG, "response error, message : ${it.message}")
            })
        )
    }

}