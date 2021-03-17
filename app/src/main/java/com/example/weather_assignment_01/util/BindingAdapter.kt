package com.example.weather_assignment_01.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather_assignment_01.R
import com.example.weather_assignment_01.data.Location
import com.example.weather_assignment_01.data.Weather
import com.example.weather_assignment_01.ui.WeatherAdapter

object BindingAdapter {

    @SuppressLint("UseCompatLoadingForDrawables")
    @JvmStatic //자바파일 있으면 이 어노테이션을 써야 나중에 데이터 못 읽는거 방지
    @BindingAdapter("app:weatherList")
    fun setWeatherAdapter(recyclerView: RecyclerView, weatherList: List<Weather.WeatherModel>){
        if(recyclerView.adapter == null){
            recyclerView.adapter = WeatherAdapter().apply{
                weathers = weatherList
            }
            recyclerView.context.getDrawable(R.drawable.decoration_line)?.run{
                val itemDecoration = TableItemDecoration(recyclerView.context).apply {
                    setDrawable(this@run)
                }
                recyclerView.addItemDecoration(itemDecoration)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:theTemp")
    fun setTemp(textView: TextView, theTemp: Double){
        textView.text = String.format(textView.context.getString(R.string.format_temp), theTemp)
    }

    @JvmStatic
    @BindingAdapter("app:humidity")
    fun setHumidity(textView: TextView, humidity: Int){
        textView.text = String.format(textView.context.getString(R.string.format_humidity), humidity)
    }

    @JvmStatic
    @BindingAdapter("app:weatherIcon")
    fun setWeatherIcon(imageView: ImageView, iconUrl: String){
        Glide.with(imageView.context)
            .load(iconUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("app:goneUnless")
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE
        else View.GONE
    }


}