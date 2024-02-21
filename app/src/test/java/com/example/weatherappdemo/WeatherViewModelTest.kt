package com.example.weatherappdemo

import com.example.weatherappdemo.data.repository.WeatherRepository
import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast
import com.example.weatherappdemo.ui.weatherscreen.WeatherState
import com.example.weatherappdemo.ui.weatherscreen.WeatherViewModel
import com.example.weatherappdemo.util.Constants
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = mockk()
        viewModel = WeatherViewModel(weatherRepository)
    }

    @Test
    fun fetchWeatherData_success() = runBlocking {
        // Mock data
        val zipCode = "12345"
        val currentWeather = UICurrentWeather(
            cityName = "Motera", temperature = 308.5f, humidity = 34
        )
        val forecast = listOf(
            UIForecast()
        )
        val expectedWeatherState =
            WeatherState(currentWeather, forecast, isLoading = false, error = "")

        // Mock repository
        coEvery { weatherRepository.getCurrentWeather(zipCode, Constants.API_KEY) } returns flowOf(
            currentWeather
        )
        coEvery { weatherRepository.getForecast(zipCode, Constants.API_KEY) } returns flowOf(
            forecast
        )

        // Call the method under test
        viewModel.fetchWeatherData(zipCode, Constants.API_KEY)
        delay(4000)
        // Verify that the weather state is updated correctly
        assertEquals(
            expectedWeatherState.currentWeather.cityName,
            viewModel.weatherState.value.currentWeather.cityName
        )
    }

    @Test
    fun fetchWeatherData_error() = runBlocking {
        // Mock data
        val zipCode = "380005,IN"
        val InvalidKey = "Invalid"
        val errorMessage = "Error fetching data"
        val expectedWeatherState = WeatherState(isLoading = false, error = errorMessage)

        // Mock repository to throw an exception
        coEvery { weatherRepository.getCurrentWeather(zipCode, InvalidKey) } throws Exception(
            errorMessage
        )
        coEvery { weatherRepository.getForecast(zipCode, InvalidKey) } returns flowOf(listOf())

        // Call the method under test
        viewModel.fetchWeatherData(zipCode, InvalidKey)

        delay(4000)
        // Verify that the weather state is updated correctly
        assertTrue(viewModel.weatherState.value.error.isNotEmpty())
    }
}
