package com.moneyminions.presentation.viewmodel.travellist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.usecase.travellist.GetTravelListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelListViewModel @Inject constructor(
    private val getTravelListUseCase: GetTravelListUseCase,
) : ViewModel() {
    
    private val _networkTravelList =
        MutableStateFlow<NetworkResult<List<TravelRoomDto>>>(NetworkResult.Idle)
    val networkTravelList = _networkTravelList.asStateFlow()
    fun getTravelList() {
        viewModelScope.launch {
            _networkTravelList.emit(getTravelListUseCase.invoke())
        }
    }
    
    private val _travelList = MutableStateFlow((emptyList<TravelRoomDto>()))
    val travelList: StateFlow<List<TravelRoomDto>> = _travelList.asStateFlow()
    
    fun refresh() {
        _travelList.update {
            sampleList()
        }
    }
    
    fun removeItem(currentItem: TravelRoomDto) {
        _travelList.update {
            val mutableList = it.toMutableList()
            mutableList.remove(currentItem)
            mutableList
        }
    }
    
    private fun sampleList() = listOf(
        TravelRoomDto(roomId = 1),
        TravelRoomDto(roomId = 2),
        TravelRoomDto(roomId = 3),
        TravelRoomDto(roomId = 4),
        TravelRoomDto(roomId = 5),
    )
    
}