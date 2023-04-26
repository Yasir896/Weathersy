package com.techlads.weathersy.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.techlads.weathersy.R
import com.techlads.weathersy.data.WeatherApi
import com.techlads.weathersy.presentation.model.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class LocationUtil {

    val dataLoaded = mutableStateOf(false)
    val data = mutableStateOf(Weather(time = "", name = "", description = "", temp = 0.0))

    fun createLocationRequest(context: Context, fusedLocationProviderClient: FusedLocationProviderClient) {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }


        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest,object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                Log.d("CurrentLocation", p0.locations.toString())
                for (location in p0.locations){
                    CoroutineScope(Dispatchers.IO).launch {
                        val weatherDTO = WeatherApi
                            .apiInstance
                            .getWeatherDetails(34.5203696,
                                74.3587473,
                                context.resources.getString(R.string.open_weather_api_key))
                        dataLoaded.value = true;
                        data.value = Weather(name = weatherDTO.name,
                            time = "${(weatherDTO.main.temp - 273).roundToInt()}°C",
                            description = weatherDTO.weather[0].description,
                            temp = (weatherDTO.main.temp - 273).roundToInt().toDouble()
                        )
                    }
                }
            }
        }, Looper.getMainLooper())
    }


}