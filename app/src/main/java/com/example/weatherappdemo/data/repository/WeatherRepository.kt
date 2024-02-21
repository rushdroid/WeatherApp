package com.example.weatherappdemo.data.repository


import com.example.weatherappdemo.data.mapper.toUIForecastList
import com.example.weatherappdemo.data.mapper.toUIWeather
import com.example.weatherappdemo.data.network.WeatherApiService
import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast
import com.example.weatherappdemo.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getCurrentWeather(zipCode: String): Flow<UICurrentWeather> = flow {
        val currentWeather = weatherApiService.getCurrentWeather(zipCode, Constants.API_KEY)
        emit(currentWeather.toUIWeather())
    }

    suspend fun getForecast(zipCode: String): Flow<List<UIForecast>> = flow {
        val forecast = weatherApiService.getForecast(zipCode, Constants.API_KEY)
        emit(forecast.toUIForecastList())
    }
}
