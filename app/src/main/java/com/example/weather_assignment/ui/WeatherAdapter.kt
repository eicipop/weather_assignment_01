package com.example.weather_assignment.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather_assignment.R
import com.example.weather_assignment.data.Weather
import com.example.weather_assignment.data.WeatherHeader
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    private val VIEW_TYPE_WEATHER: Int = R.layout.item_weather
    private val VIEW_TYPE_HEADING: Int = R.layout.item_header

    private val differ = AsyncListDiffer(this, DiffCallback)

    var weathers: List<Weather> = Collections.emptyList()
        set(value) {
            field = value
            differ.submitList(buildMergedList(weatherList = value))
        }

    init {
        differ.submitList(buildMergedList())
    }

    private fun buildMergedList(
        // 날씨에 대한 리스트
        weatherList: List<Weather> = weathers
    ): MutableList<Any> {
        val merged = mutableListOf<Any>()
        // 날씨 리스트가 있으면 다른 종류의 data 인 WeatherHeader 를 같이 합쳐서 리스트로 사용
        if (weatherList.isNotEmpty()) {
            merged += WeatherHeader(
                R.string.local,
                R.string.today,
                R.string.tomorrow
            )
            merged.addAll(weatherList)
        }
        return merged
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            VIEW_TYPE_WEATHER -> WeatherViewHolder.WeatherInfoViewHolder(
                inflater.inflate(VIEW_TYPE_WEATHER, parent, false)
            )
            VIEW_TYPE_HEADING ->WeatherViewHolder.WeatherHeaderViewHolder(
                inflater.inflate(VIEW_TYPE_HEADING, parent, false)
            )
            else -> throw IllegalStateException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        when(holder){
            is WeatherViewHolder.WeatherInfoViewHolder -> holder.bind(differ.currentList[position] as Weather)
            is WeatherViewHolder.WeatherHeaderViewHolder -> holder.bind(differ.currentList[position] as WeatherHeader)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(differ.currentList[position]){
            is Weather -> VIEW_TYPE_WEATHER
            is WeatherHeader -> VIEW_TYPE_HEADING
            else -> throw  IllegalStateException("Unknown view type at position $position")
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    sealed class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class WeatherInfoViewHolder(itemView: View) : WeatherViewHolder(itemView) {
            private val tvTitle: TextView by lazy { itemView.findViewById<TextView>(R.id.tvTitle) }
            private val tvWeatherStateToday: TextView by lazy {
                itemView.findViewById<ConstraintLayout>(
                    R.id.weatherToday
                ).findViewById<TextView>(R.id.tvWeatherState)
            }
            private val tvWeatherTempToday: TextView by lazy {
                itemView.findViewById<ConstraintLayout>(R.id.weatherToday)
                    .findViewById<TextView>(R.id.tvWeatherTemp)
            }
            private val tvWeatherHumidityToday: TextView by lazy {
                itemView.findViewById<ConstraintLayout>(
                    R.id.weatherToday
                ).findViewById<TextView>(R.id.tvWeatherHumidity)
            }
            private val ivWeatherIconToday: ImageView by lazy {
                itemView.findViewById<ConstraintLayout>(R.id.weatherToday)
                    .findViewById<ImageView>(R.id.ivWeatherIcon)
            }
            private val tvWeatherStateTomorrow: TextView by lazy {
                itemView.findViewById<ConstraintLayout>(
                    R.id.weatherTomorrow
                ).findViewById<TextView>(R.id.tvWeatherState)
            }
            private val tvWeatherTempTomorrow: TextView by lazy {
                itemView.findViewById<ConstraintLayout>(
                    R.id.weatherTomorrow
                ).findViewById<TextView>(R.id.tvWeatherTemp)
            }
            private val tvWeatherHumidityTomorrow: TextView by lazy {
                itemView.findViewById<ConstraintLayout>(
                    R.id.weatherTomorrow
                ).findViewById<TextView>(R.id.tvWeatherHumidity)
            }
            private val ivWeatherIconTomorrow: ImageView by lazy {
                itemView.findViewById<ConstraintLayout>(R.id.weatherTomorrow)
                    .findViewById<ImageView>(R.id.ivWeatherIcon)
            }

            fun bind(item: Weather) {
                tvTitle.text = item.title

                item.consolidatedWeather[0].apply {
                    tvWeatherStateToday.text = weatherStateName
                    tvWeatherTempToday.text =
                        String.format(itemView.context.getString(R.string.format_temp), theTemp)
                    tvWeatherHumidityToday.text =
                        String.format(itemView.context.getString(R.string.format_humidity), humidity)
                    weatherIcon(ivWeatherIconToday, iconUrl)
                }

                item.consolidatedWeather[1].apply {
                    tvWeatherStateTomorrow.text = weatherStateName
                    tvWeatherTempTomorrow.text =
                        String.format(itemView.context.getString(R.string.format_temp), theTemp)
                    tvWeatherHumidityTomorrow.text =
                        String.format(itemView.context.getString(R.string.format_humidity), humidity)
                    weatherIcon(ivWeatherIconTomorrow, iconUrl)
                }
            }

            private fun weatherIcon(imageView: ImageView, iconUrl: String) {
                Glide.with(imageView.context)
                    .load(iconUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)
            }

        }

        class WeatherHeaderViewHolder(itemView: View):WeatherViewHolder(itemView){
            private val tvLocal:TextView by lazy { itemView.findViewById<TextView>(R.id.tvLocal) }
            private val tvToday:TextView by lazy { itemView.findViewById<TextView>(R.id.tvToday) }
            private val tvTomorrow:TextView by lazy { itemView.findViewById<TextView>(R.id.tvTomorrow) }

            fun bind(weatherHeader: WeatherHeader){
                tvLocal.text = itemView.resources.getString(weatherHeader.titleLocal)
                tvToday.text = itemView.resources.getString(weatherHeader.titleToday)
                tvTomorrow.text = itemView.resources.getString(weatherHeader.titleTomorrow)
            }
        }
    }
    /**
     * Diff items presented by this adapter.
     */
    object DiffCallback : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Weather && newItem is Weather -> true
                oldItem is WeatherHeader && newItem is WeatherHeader -> oldItem == newItem
                else -> false
            }
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return when {
                oldItem is Weather && newItem is Weather -> oldItem == newItem
                else -> true
            }
        }
    }
}