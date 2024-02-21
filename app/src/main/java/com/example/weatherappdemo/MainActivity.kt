package com.example.weatherappdemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherappdemo.ui.theme.WeatherAppDemoTheme
import com.example.weatherappdemo.ui.weatherscreen.WeatherScreen
import com.example.weatherappdemo.ui.weatherscreen.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppDemoTheme {
                WeatherScreen(viewModel = viewModel)
            }
        }
    }
}