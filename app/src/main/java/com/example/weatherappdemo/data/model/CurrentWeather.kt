package com.example.weatherappdemo.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("name")
    val cityName: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Main(
    @SerializedName("temp")
    val temperature: Double,
    @SerializedName("humidity")
    val humidity: Int
)

data class Weather(
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String
)
