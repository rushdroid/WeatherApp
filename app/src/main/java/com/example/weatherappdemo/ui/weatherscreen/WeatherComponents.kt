package com.example.weatherappdemo.ui.weatherscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherappdemo.ui.model.UICurrentWeather
import com.example.weatherappdemo.ui.model.UIForecast
import com.example.weatherappdemo.ui.theme.WeatherAppDemoTheme
import com.example.weatherappdemo.util.Constants
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {
    val weatherState by viewModel.weatherState.collectAsState()
    val scaffoldState = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.fetchWeatherData(Constants.ZIP_CODE, Constants.API_KEY)
    }

    LaunchedEffect(weatherState.error.isNotEmpty()) {
        if (weatherState.error.isNotEmpty()) {
            scaffoldState.launch {
                val result = snackbarHostState.showSnackbar(
                    message = weatherState.error, actionLabel = "Try Again",
                    duration = SnackbarDuration.Long
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        viewModel.fetchWeatherData(Constants.ZIP_CODE, Constants.API_KEY)
                    }

                    SnackbarResult.Dismissed -> {
                        /* Handle snackbar dismissed */
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        },
        modifier = Modifier.fillMaxSize(),
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .background(color = Color.White)
            ) {
                if (weatherState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    CurrentWeatherSection(weatherState.currentWeather)
                    ForecastListSection(weatherState.forecast)
                }
            }
        },
    )
}

@Composable
fun CurrentWeatherSection(
    currentWeather: UICurrentWeather = UICurrentWeather()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        currentWeather.let {
            Text(
                text = it.cityName,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "${it.temperature}°C",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp)
            )
            Text(
                text = "Humidity: ${it.humidity}%",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}


@Composable
fun ForecastListSection(
    forecastList: List<UIForecast> = listOf()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "Forecast", modifier = Modifier.padding(start = 16.dp), style = TextStyle(
                fontSize = 16.sp, color = Color.Black
            )
        )
        ForcastList(forecastList)
    }
}

@Composable
fun ForcastList(forecastList: List<UIForecast> = listOf()) {
    LazyColumn {
        items(forecastList) { item ->
            ListItem(forecastItem = item)
        }
    }
}

@Composable
fun ListItem(forecastItem: UIForecast) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF87CEEB), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(text = forecastItem.dateTime, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = forecastItem.temperature.toString() + "°C",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppDemoTheme {
        WeatherScreen()
    }
}