package com.techlads.weathersy.presentation

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.rememberScalingLazyListState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.techlads.weathersy.utils.LocationUtil

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeathersyApp(locationUtil: LocationUtil) {
    
    val listState = rememberScalingLazyListState()
    
    MaterialTheme() {

        val locationPermissionState = rememberMultiplePermissionsState(
            permissions =  listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        val contentModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
        val iconModifier = Modifier
            .size(24.dp)
            .wrapContentSize(align = Alignment.Center)

        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 32.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.Bottom,
            state = listState,
        ) {
            item { Spacer(modifier = Modifier.size(20.dp))}

            if (locationPermissionState.allPermissionsGranted) {
                if(locationUtil.dataLoaded.value) {
                    item { TextWidget(modifier = contentModifier,
                        "Location Permission Granter") }
                } else {
                        item { WeathersyCard(
                            modifier = contentModifier,
                            title = locationUtil.data.value.name ,
                            weatherDescription =  locationUtil.data.value.description,
                            time = locationUtil.data.value.time , temperature = 12.0)
                        }
                    }
            } else {
                val allPermissionsRevoked = locationPermissionState.permissions.size ==
                        locationPermissionState.revokedPermissions.size

                val textToShow = if (!allPermissionsRevoked) {
                        "Thanks for allowing to access you location."
                } else if (locationPermissionState.shouldShowRationale){
                    "Accessing you location is importate for app functionality."
                } else {
                    "This feature requires location permission."
                }

                item { TextWidget(contentModifier,textToShow) }
                item { WeathersyButton(contentModifier, iconModifier){
                    locationPermissionState.launchMultiplePermissionRequest()
                } }
            }
        }
    }
}