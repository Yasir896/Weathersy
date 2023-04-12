package com.techlads.weathersy.data.dto

import com.techlads.weathersy.data.WeatherApi

data class WeatherDto(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coords: Coordinates,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timeZone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)