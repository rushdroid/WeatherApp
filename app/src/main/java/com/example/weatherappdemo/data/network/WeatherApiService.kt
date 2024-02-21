package com.example.weatherappdemo.data.network

import com.example.weatherappdemo.data.model.CurrentWeather
import com.example.weatherappdemo.data.model.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("zip") zipCode: String,
        @Query("APPID") apiKey: String
    ): CurrentWeather

    @GET("forecast")
    suspend fun getForecast(
        @Query("zip") zipCode: String,
        @Query("APPID") apiKey: String
    ): Forecast
}
