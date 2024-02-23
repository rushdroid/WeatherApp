package com.example.weatherappdemo

import com.example.weatherappdemo.data.network.WeatherApiService
import com.example.weatherappdemo.data.repository.WeatherRepository
import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeWeatherRepository(
    weatherApiService: WeatherApiService
) : WeatherRepository(weatherApiService) {

    var isSuccess = true
    override suspend fun getCurrentWeather(
        zipCode: String
    ): Flow<UICurrentWeather> {
        if (!isSuccess) {
            throw Exception(
                "Failed to get data"
            )
        }
        return flowOf(
            UICurrentWeather(
                cityName = "Motera",
                humidity = 30,
                temperature = 308.5f
            )
        )
    }

    override suspend fun getForecast(
        zipCode: String
    ): Flow<List<UIForecast>> {
        if (!isSuccess) {
            throw Exception(
                "Failed to get data"
            )
        }
        return flowOf(listOf())
    }
}

