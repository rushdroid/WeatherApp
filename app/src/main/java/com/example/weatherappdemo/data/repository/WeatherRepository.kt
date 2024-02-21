package com.example.weatherappdemo.data.repository


import com.example.weatherappdemo.data.mapper.toUIForecastList
import com.example.weatherappdemo.data.mapper.toUIWeather
import com.example.weatherappdemo.data.network.WeatherApiService
import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    val weatherApiService: WeatherApiService
) {
    suspend fun getCurrentWeather(zipCode: String, apiKey: String): Flow<UICurrentWeather> = flow {
        val currentWeather = weatherApiService.getCurrentWeather(zipCode, apiKey)
        emit(currentWeather.toUIWeather())
    }

    suspend fun getForecast(zipCode: String, apiKey: String): Flow<List<UIForecast>> = flow {
        val forecast = weatherApiService.getForecast(zipCode, apiKey)
        emit(forecast.toUIForecastList())
    }
}
