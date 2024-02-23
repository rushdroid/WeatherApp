package com.example.weatherappdemo.ui.weatherscreen

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

    fun fetchWeatherData(zipCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _weatherState.value = _weatherState.value.copy(
                isLoading = true,
                hasError = false,
                isDataFetched = false,
                error = ""
            )
            try {
                val zip = "${zipCode},IN"
                combine(
                    weatherRepository.getCurrentWeather(zip),
                    weatherRepository.getForecast(zip)
                ) { currentWeather, forecast ->
                    WeatherState(
                        currentWeather,
                        forecast,
                        hasError = false,
                        isDataFetched = true,
                        isLoading = false,
                        zipCode = zipCode,
                        error = ""
                    )
                }.catch { exception ->
                    exception.printStackTrace()
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        hasError = true,
                        isDataFetched = false,
                        error = "Error while fetching weather data"
                    )
                }.collect { weatherState ->
                    _weatherState.value = weatherState
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _weatherState.value = _weatherState.value.copy(
                    isLoading = false,
                    isDataFetched = false,
                    hasError = true,
                    error = "Error fetching weather data"
                )
            }
        }
    }

    fun saveZipCode(zipCode: String) {
        _weatherState.value = _weatherState.value.copy(
            zipCode = zipCode
        )
    }

    fun showError(errorString: String) {
        _weatherState.value = _weatherState.value.copy(
            error = errorString
        )
    }
}
