package com.moneyminions.presentation.screen.travelmap

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.moneyminions.domain.model.travelmap.LocationDto
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.domain.model.travelmap.TravelMapSpotDto
import com.moneyminions.domain.model.travelmap.TravelMapStoreAddressDto
import com.moneyminions.presentation.R
import com.moneyminions.presentation.utils.NetworkResultHandler
import com.moneyminions.presentation.viewmodel.travelmap.TravelMapViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage

private const val TAG = "D210"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun TravelMapScreen(
    roomId: Int,
    type: String?,
    modifier: Modifier = Modifier,
    travelMapViewModel: TravelMapViewModel = hiltViewModel(),
) {
    Log.d(TAG, "TravelMapScreen: roomId $roomId  type $type")
    val locationSource = rememberFusedLocation()
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = true),
        )
    }
    val travelMapGetState by travelMapViewModel.travelSpotGetState.collectAsState()
    var travelSpotList by remember {
        mutableStateOf(listOf(TravelMapSpotDto()))
    }
    val travelSpotDetailGetState by travelMapViewModel.travelSpotDetailGetState.collectAsState()
    var travelSpotDetailInfoList by remember {
        mutableStateOf(listOf(TravelMapDetailDto()))
    }
    var travelSpotLatLongitudeList by remember {
        mutableStateOf(listOf(LocationDto()))
    }
    var centerPlace by remember {
        mutableStateOf(LocationDto(latitude = 35.9078, longitude = 127.7669))
    }
    var cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(centerPlace.latitude, centerPlace.longitude), 6.0)
    }

    var spotDetailDialog by remember {
        mutableStateOf(false)
    }
    if (spotDetailDialog) {
        TravelSpotDetailDialog(spotInfoList = travelSpotDetailInfoList) {
            spotDetailDialog = false
            travelMapViewModel.initTravelSpotDetailGetState()
        }
    }

    val context = LocalContext.current
    NetworkResultHandler(state = travelMapGetState, errorAction = { /*TODO*/ }, successAction = {
        Log.d(TAG, "TravelMapScreen: $it")
        travelSpotList = it
        if (travelSpotList.isNotEmpty() && travelSpotList[0].storeAddress != "") {
            travelSpotLatLongitudeList = travelMapViewModel.setAddressToLatLongitude(
                context = context,
                spotList = travelSpotList,
            )
            centerPlace = travelMapViewModel.calculateCenterLocation(travelSpotLatLongitudeList)
            cameraPositionState.position = CameraPosition(LatLng(centerPlace.latitude, centerPlace.longitude), 11.0)
            Log.d(TAG, "TravelMapScreen: ${centerPlace.latitude} ${centerPlace.longitude}")
            Log.d(TAG, "TravelMapScreen: list 값 $travelSpotLatLongitudeList")
        }
    })
    NetworkResultHandler(
        state = travelSpotDetailGetState,
        errorAction = { /*TODO*/ },
        successAction = {
            travelSpotDetailInfoList = it
            spotDetailDialog = true
        },
    )
    LaunchedEffect(Unit) {
        travelMapViewModel.getTravelSpotList(roomId)
    }

    NaverMap(
        modifier = modifier.fillMaxSize(),
        locationSource = locationSource,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            locationTrackingMode = if (type == "home") LocationTrackingMode.Follow else LocationTrackingMode.None,
        ),
        uiSettings = mapUiSettings,
    ) {
        if (travelSpotLatLongitudeList.size > 0) {
            MapMarkers(
                locations = travelSpotLatLongitudeList,
                cameraPositionState = cameraPositionState,
                markerClick = { locationInfo ->
                    travelMapViewModel.getTravelSpotDetail(
                        roomId = roomId,
                        travelMapStoreAddressDto = TravelMapStoreAddressDto(storeAddress = locationInfo.storeAddress),
                    )
                },
            )
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapMarkers(
    locations: List<LocationDto>,
    cameraPositionState: CameraPositionState,
    markerClick: (LocationDto) -> Unit,
) {
    val scope = rememberCoroutineScope()
    locations.forEachIndexed { idx, item ->
        if (item.storeCategory != "") {
            Marker(
                state = MarkerState(position = LatLng(item.latitude, item.longitude)),
                icon = OverlayImage.fromResource(getMarkerIcon(item.storeCategory)),
                width = 24.dp,
                height = 24.dp,
                onClick = {
                    markerClick(item)
                    cameraPositionState.position =
                        CameraPosition(LatLng(item.latitude, item.longitude), 14.0)
                    Log.d(TAG, "MapMarkers: $item")
                    true
                },
            )
        }
    }
}

private fun getMarkerIcon(category: String): Int {
    return when (category) {
        "식비" -> R.drawable.marker_dining
        "커피와 디저트" -> R.drawable.marker_coffee
        "주류" -> R.drawable.marker_alcohol
        "마트" -> R.drawable.marker_groceries
        "편의점" -> R.drawable.marker_minimarts
        "교통/자동차" -> R.drawable.marker_transportaion
        "숙소" -> R.drawable.marker_accommodation
        "쇼핑" -> R.drawable.marker_shopping
        "레저" -> R.drawable.marker_leisure
        else -> R.drawable.marker_uncategorized
    }
}
