package com.techlads.weathersy.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.AppCard
import androidx.wear.compose.material.Text
import com.techlads.weathersy.R

@Composable
fun WeathersyCard(
    modifier: Modifier = Modifier,
    title: String,
    weatherDescription: String,
    time: String,
    temperature: Double
) {
    AppCard(
        modifier = modifier,
        appName = { Text("Weathersy") },
        time = { Text(text = time, color = if(temperature < 20) Color.White else Color.Red)},
        title = { Text(text = title, color = Color.Yellow)},
        onClick = {  }) {
        val icon = if(temperature < 20.0) R.mipmap.cold else R.mipmap.hot
        
        Row(horizontalArrangement = Arrangement.Center) {
            Image(
                modifier = Modifier.height(20.dp),
                painter = painterResource(id = icon),
                contentDescription = "temperature_icon")
            
            Spacer(modifier = Modifier.size(5.dp))
            
            Text(text = weatherDescription)
        }
    }
}