package com.moneyminions.presentation.viewmodel.travelmap

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travelmap.LocationDto
import com.moneyminions.domain.model.travelmap.TravelMapDetailDto
import com.moneyminions.domain.model.travelmap.TravelMapSpotDto
import com.moneyminions.domain.model.travelmap.TravelMapStoreAddressDto
import com.moneyminions.domain.usecase.travelmap.GetTravelSpotDetailUseCase
import com.moneyminions.domain.usecase.travelmap.GetTravelSpotsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D210"
@HiltViewModel
class TravelMapViewModel @Inject constructor(
    private val getTravelSpotsUseCase: GetTravelSpotsUseCase,
    private val getTravelSpotDetailUseCase: GetTravelSpotDetailUseCase,
) : ViewModel() {
    private val _travelSpotGetState = MutableStateFlow<NetworkResult<List<TravelMapSpotDto>>>(NetworkResult.Idle)
    val travelSpotGetState = _travelSpotGetState.asStateFlow()
    
    private val _travelSpotDetailGetState = MutableStateFlow<NetworkResult<List<TravelMapDetailDto>>>(NetworkResult.Idle)
    val travelSpotDetailGetState = _travelSpotDetailGetState.asStateFlow()
    fun initTravelSpotDetailGetState(){
        viewModelScope.launch {
            _travelSpotDetailGetState.emit(NetworkResult.Idle)
        }
    }
    
    fun getTravelSpotList(roomId: Int) = viewModelScope.launch { 
        _travelSpotGetState.value = NetworkResult.Loading
        _travelSpotGetState.emit(getTravelSpotsUseCase.invoke(roomId = roomId))
        Log.d(TAG, "getTravelSpotList: ${travelSpotGetState.value}")
    }
    
    fun getTravelSpotDetail(roomId: Int, travelMapStoreAddressDto: TravelMapStoreAddressDto) = viewModelScope.launch { 
        _travelSpotDetailGetState.value = NetworkResult.Loading
        _travelSpotDetailGetState.emit(getTravelSpotDetailUseCase.invoke(roomId = roomId, travelMapStoreAddressDto = travelMapStoreAddressDto))
        Log.d(TAG, "getTravelSpotDetail: ${travelSpotDetailGetState.value}")
    }
    
    fun setAddressToLatLongitude(
        context: Context,
        spotList: List<TravelMapSpotDto>
    ): MutableList<LocationDto> {
        Log.d(TAG, "setAddressToLatLongitude: $spotList")
        val locationList = mutableListOf<LocationDto>()

        for (spot in spotList) {
            if(!spot.storeAddress.isNullOrEmpty()) {
                val category = if(spot.storeSector.isNullOrEmpty()) "" else spot.storeSector!!
                val location = searchAddress(context, spot.storeAddress!!, category)
                locationList.add(location)
            }
        }
        return locationList
    }

    fun calculateCenterLocation(spotList: List<LocationDto>): LocationDto {
        if(spotList.isEmpty()) return LocationDto(latitude = 35.9078, longitude = 127.7669)

        var totalLatitude = 0.0
        var totalLongitude = 0.0

        for (location in spotList) {
            if(!location.storeAddress.isNullOrEmpty()) {
                totalLatitude += location.latitude
                totalLongitude += location.longitude
            }
        }
        val centerLatitude = totalLatitude / spotList.size
        val centerLongitude = totalLongitude / spotList.size

        Log.d(TAG, "calculateCenterLocation: $totalLatitude $totalLongitude")
        Log.d(TAG, "calculateCenterLocation: $centerLatitude $centerLongitude")

        return LocationDto(latitude = centerLatitude, longitude = centerLongitude)
    }
}

private fun searchAddress(
    context: Context,
    address: String = "",
    category: String = "",
): LocationDto {
    if (Build.VERSION.SDK_INT < 33) {
        val list = address?.let { Geocoder(context).getFromLocationName(it, 1) }!!
        Log.d(TAG, "address list : $list")
        return LocationDto(storeAddress = address, storeCategory = category, latitude = list[0].latitude, longitude = list[0].longitude)
    } else {
        val list = address?.let { Geocoder(context).getFromLocationName(it, 1) }!!
        Log.d(TAG, "searchAddress: address to  ${list[0].latitude}  ${list[0].longitude}")
        return LocationDto(storeAddress = address, storeCategory = category, latitude = list[0].latitude, longitude = list[0].longitude)
    }
}