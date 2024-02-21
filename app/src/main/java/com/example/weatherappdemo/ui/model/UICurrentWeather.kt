package com.example.weatherappdemo.ui.model

data class UICurrentWeather(
    val cityName: String = "",
    val temperature: Float = 0f,
    val humidity: Int = 0
)