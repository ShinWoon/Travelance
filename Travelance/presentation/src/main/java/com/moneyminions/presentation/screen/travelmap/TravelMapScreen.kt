package com.moneyminions.presentation.screen.travelmap

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun TravelMapScreen(
    navController: NavHostController,
    type: String?,
    modifier: Modifier = Modifier,
) {
    val locationSource = rememberFusedLocation()
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = true),
        )
    }
    NaverMap(
        modifier = modifier.fillMaxSize(),
        locationSource = locationSource,
        properties = MapProperties(
            locationTrackingMode = if(type == "home") LocationTrackingMode.Follow else LocationTrackingMode.None
        ),
        uiSettings = mapUiSettings,
    ) {
//
    }
}
