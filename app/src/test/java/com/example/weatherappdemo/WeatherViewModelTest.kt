package com.example.weatherappdemo

import com.example.weatherappdemo.data.network.WeatherApiService
import com.example.weatherappdemo.ui.weatherscreen.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherApiService: WeatherApiService
    private lateinit var fakeRepository: FakeWeatherRepository

    @Before
    fun setup() {
        weatherApiService = mock<WeatherApiService>()
        fakeRepository = FakeWeatherRepository(weatherApiService)
        viewModel = WeatherViewModel(fakeRepository)
    }

    @Test
    fun fetchWeatherData_success() = runBlocking {
        fakeRepository.isSuccess = true
        val zipCode = "380005"
        viewModel.saveZipCode(zipCode)
        viewModel.fetchWeatherData()
        delay(10000)
        assert(
            viewModel.weatherState.value.currentWeather.cityName.isNotEmpty()
        )
    }

    @Test
    fun fetchWeatherData_error() = runBlocking {
        fakeRepository.isSuccess = false
        val zipCode = "380005"
        viewModel.saveZipCode(zipCode)
        viewModel.fetchWeatherData()
        delay(4000)
        assertTrue(viewModel.weatherState.value.error.isNotEmpty())
    }

    @Test
    fun `test saveZipCode`() = runBlocking {
        val zipCode = "12345"
        viewModel.saveZipCode(zipCode)

        // Verify saved zip code
        assertEquals(zipCode, viewModel.weatherState.value.zipCode)
    }

    @Test
    fun `test showError`() = runBlocking {
        // Call showError
        val errorMessage = "An error occurred"
        viewModel.showError(errorMessage)

        // Verify error state
        assertEquals(errorMessage, viewModel.weatherState.value.error)
    }
}
