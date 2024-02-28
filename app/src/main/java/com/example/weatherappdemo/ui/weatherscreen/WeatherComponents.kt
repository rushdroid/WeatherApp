package com.example.weatherappdemo.ui.weatherscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherappdemo.ui.model.UIForecast
import com.example.weatherappdemo.ui.theme.WeatherAppDemoTheme
import kotlinx.coroutines.launch

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val weatherState by viewModel.weatherState.collectAsState()
    val scaffoldState = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(weatherState.error.isNotEmpty()) {
        if (weatherState.error.isNotEmpty()) {
            scaffoldState.launch {
                val result = snackbarHostState.showSnackbar(
                    message = weatherState.error,
                    actionLabel = "Try Again",
                    duration = SnackbarDuration.Short
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        if (weatherState.zipCode.isNotEmpty())
                            viewModel.fetchWeatherData()
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
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
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
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(it),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = weatherState.zipCode,
                            onValueChange = { newValue ->
                                viewModel.saveZipCode(newValue)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("Enter zipcode (only INDIA)", color = Color.Gray) },
                            modifier = Modifier
                                .weight(0.7f)
                        )
                        Spacer(
                            modifier = Modifier.width(16.dp)
                        )
                        Button(
                            onClick = {
                                keyboardController?.hide()
                                if (weatherState.zipCode.isNotEmpty()) {
                                    viewModel.fetchWeatherData()
                                } else {
                                    viewModel.showError(errorString = "Please enter valid zipcode")
                                }
                            }, modifier = Modifier.weight(0.3f)
                        ) {
                            Text("Submit")
                        }
                    }
                    CurrentWeatherSection(weatherState)
                    ForecastListSection(weatherState)
                }
            }
        },
    )
}

@Composable
fun CurrentWeatherSection(
    weatherState: WeatherState = WeatherState()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherState.let {
            Text(
                text = it.currentWeather.cityName,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = if (it.isDataFetched.not() || it.hasError) "--" else "${it.currentWeather.temperature}°C",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 24.sp)
            )
            Text(
                text = "Humidity: ${
                    if (it.isDataFetched.not() || it.hasError) {
                        "--"
                    } else {
                        "${it.currentWeather.humidity}%"
                    }
                }",
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}


@Composable
fun ForecastListSection(
    weatherState: WeatherState = WeatherState()
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
        ForcastList(weatherState.forecast)
    }
}

@Composable
fun ForcastList(
    forecastList: List<UIForecast> = listOf()
) {
    LazyColumn {
        items(forecastList) { item ->
            ListItem(forecastItem = item)
        }
    }
}

@Composable
fun ListItem(
    forecastItem: UIForecast
) {
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