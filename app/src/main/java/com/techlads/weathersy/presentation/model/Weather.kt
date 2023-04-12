package com.techlads.weathersy.presentation.model

data class Weather(
    val time: String,
    val name: String,
    val description: String,
    val temp: Double
)