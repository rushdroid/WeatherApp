package com.example.weatherappdemo.ui.weatherscreen

import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast

data class WeatherState(
    val currentWeather: UICurrentWeather = UICurrentWeather(),
    val forecast: List<UIForecast> = listOf(),
    val zipCode: String = "",
    val isLoading: Boolean = false,
    val isDataFetched: Boolean = false,
    val hasError: Boolean = false,
    val error: String = ""
)
