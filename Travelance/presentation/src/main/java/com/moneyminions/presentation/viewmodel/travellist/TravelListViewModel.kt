package com.moneyminions.presentation.viewmodel.travellist

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.usecase.preference.GetTravelingRoomIdUseCase
import com.moneyminions.domain.usecase.preference.PutTravelingRoomIdUseCase
import com.moneyminions.domain.usecase.travellist.DeleteTravelRoomUseCase
import com.moneyminions.domain.usecase.travellist.GetTravelListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TravelListViewModel_D210"
@HiltViewModel
class TravelListViewModel @Inject constructor(
    private val getTravelListUseCase: GetTravelListUseCase,
    private val deleteTravelRoomUseCase: DeleteTravelRoomUseCase,
    private val putTravelingRoomIdUseCase: PutTravelingRoomIdUseCase,
    private val getTravelingRoomIdUseCase: GetTravelingRoomIdUseCase,
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
    fun initTravelListResult(){
        viewModelScope.launch {
            _networkTravelList.emit(NetworkResult.Idle)
        }
    }
    
    private val _travelList = mutableStateOf(mutableListOf<TravelRoomDto>())
    val travelList: State<MutableList<TravelRoomDto>> = _travelList

    /**
     * 여행방 갱신 & 정산중인 여행 상태 check
     */
    fun refresh(travelList: MutableList<TravelRoomDto>) {
        _travelList.value = travelList
    
        val travelingRoomId = getTravelingRoomIdUseCase.invoke()
    
        travelList.forEach{
            // 현재 진행 중인 방과 ID가 똑같으면서 && 진행 상태가 NOW가 아니라면 -> 진행 상태를 갱신
            if(travelingRoomId == it.roomId && it.isDone != "NOW") {
                putTravelingRoomIdUseCase(0)
            }
        
            // 진행 중인 여행방 id 갱신
            if(it.isDone == "NOW") {
                putTravelingRoomIdUseCase(it.roomId)
            }
        }
    }
    
    /**
     * 여행방 삭제 API
     */
    private val _deleteTravelRoomResult = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val deleteTravelRoomResult = _deleteTravelRoomResult.asStateFlow()
    fun removeItem(currentItem: TravelRoomDto) {
        Log.d(TAG, "removeItem: ${currentItem.roomId}")
        
        // 진행 중인 여행방 삭제라면..
        if(currentItem.roomId == getTravelingRoomIdUseCase.invoke()){
            putTravelingRoomIdUseCase.invoke(0)
        }
        
        viewModelScope.launch {
            _deleteTravelRoomResult.emit(deleteTravelRoomUseCase.invoke(roomId = currentItem.roomId))
        }

//        _travelList.update {
//            val mutableList = it.toMutableList()
//            mutableList.remove(currentItem)
//            mutableList
//        }
    }

    
}