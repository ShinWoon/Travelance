package com.moneyminions.presentation.viewmodel.travellist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.usecase.travellist.DeleteTravelRoomUseCase
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
    private val deleteTravelRoomUseCase: DeleteTravelRoomUseCase
) : ViewModel() {

    /**
     * 여행방 목록 조회 API
     */
    private val _networkTravelList = MutableStateFlow<NetworkResult<List<TravelRoomDto>>>(NetworkResult.Idle)
    val networkTravelList = _networkTravelList.asStateFlow()
    fun getTravelList() {
        viewModelScope.launch {
            _networkTravelList.emit(getTravelListUseCase.invoke())
        }
    }
    
    private val _travelList = MutableStateFlow((emptyList<TravelRoomDto>()))
    val travelList: StateFlow<List<TravelRoomDto>> = _travelList.asStateFlow()

    /**
     * 여행방 갱신
     */
    fun refresh(travelList: MutableList<TravelRoomDto>) {
        _travelList.update {
            travelList
        }
    }

    /**
     * 여행방 삭제 API
     */
    private val _deleteTravelRoomResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val deleteTravelRoomResult = _deleteTravelRoomResult.asStateFlow()
    fun removeItem(currentItem: TravelRoomDto) {
        viewModelScope.launch {
            _deleteTravelRoomResult.emit(deleteTravelRoomUseCase.invoke(roomId = currentItem.roomId))
        }

        _travelList.update {
            val mutableList = it.toMutableList()
            mutableList.remove(currentItem)
            mutableList
        }
    }


}