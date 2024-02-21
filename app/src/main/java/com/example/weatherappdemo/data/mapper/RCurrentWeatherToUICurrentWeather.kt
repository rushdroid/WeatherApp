package com.example.weatherappdemo.data.mapper

import com.example.weatherappdemo.data.model.CurrentWeather
import com.example.weatherappdemo.data.model.Forecast
import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast
import java.text.SimpleDateFormat
import java.util.Locale

fun CurrentWeather.toUIWeather(): UICurrentWeather {
    return UICurrentWeather(
        cityName = this.cityName,
        temperature = (this.main.temperature - 273.15).toFloat(),
        humidity = this.main.humidity
    )
}

fun Forecast.toUIForecastList(): List<UIForecast> {
    return this.forecastList.map { forecastItem ->
        UIForecast(
            dateTime = formatDate(forecastItem.dateTime),
            temperature = (forecastItem.main.temperature - 273.15).toFloat()
        )
    }
}

private fun formatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date)
}