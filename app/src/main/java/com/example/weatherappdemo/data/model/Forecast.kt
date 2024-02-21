package com.example.weatherappdemo.data.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("list")
    val forecastList: List<ForecastItem>
)

data class ForecastItem(
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("dt_txt")
    val dateTime: String
)
