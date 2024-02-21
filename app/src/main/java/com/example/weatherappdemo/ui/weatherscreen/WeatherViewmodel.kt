package com.example.weatherappdemo.ui.weatherscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappdemo.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState

    fun fetchWeatherData(zipCode: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherState.value = _weatherState.value.copy(
                isLoading = true,
                error = ""
            )
            try {
                combine(
                    weatherRepository.getCurrentWeather(zipCode, apiKey),
                    weatherRepository.getForecast(zipCode, apiKey)
                ) { currentWeather, forecast ->
                    WeatherState(
                        currentWeather,
                        forecast,
                        isLoading = false,
                        error = ""
                    )
                }.catch { exception ->
                    exception.printStackTrace()
                    Log.e("WeatherData", "Error fetching weather data: ${exception.message}")
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        error = "Error fetching weather data"
                    )
                }.collect { weatherState ->
                    _weatherState.value = weatherState
                }
            } catch (e: Exception) {
                _weatherState.value = _weatherState.value.copy(
                    isLoading = false,
                    error = "Error fetching weather data"
                )
            }

        }
    }
}
