package com.moneyminions.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.model.travellist.TravelRoomDto
import com.moneyminions.domain.usecase.preference.GetJwtTokenUseCase
import com.moneyminions.domain.usecase.preference.GetRoleUseCase
import com.moneyminions.domain.usecase.preference.GetTravelingRoomIdUseCase
import com.moneyminions.domain.usecase.preference.PutTravelingRoomIdUseCase
import com.moneyminions.domain.usecase.travellist.GetTravelListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainViewModel_D210"
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getRoleUseCase: GetRoleUseCase,
    private val getJwtTokenUseCase: GetJwtTokenUseCase,
    private val getTravelingRoomIdUseCase: GetTravelingRoomIdUseCase,
    private val putTravelingRoomIdUseCase: PutTravelingRoomIdUseCase,
    private val getTravelListUseCase: GetTravelListUseCase,
): ViewModel() {

//    fun getRole(): String{
//        return getRoleUseCase.invoke()
//    }
    fun getJwtToken(): JwtTokenDto {
        return getJwtTokenUseCase.invoke()
    }
    
    // 진행중인 여행방 check
    fun putTravelingRoomId(roomId: Int) {
        putTravelingRoomIdUseCase.invoke(roomId = roomId)
    }
    fun getTravelingRoomId(): Int {
        return getTravelingRoomIdUseCase.invoke()
    }
    
    // 선택한 여행 방 번호
    private val _selectRoomId = mutableStateOf(0)
    val selectRoomId: State<Int> = _selectRoomId
    fun setSelectRoomId(roomId: Int) {
        _selectRoomId.value = roomId
    }

    // 초대 받은 방 번호
    private val _inviteRoomId = mutableStateOf(0)
    val inviteRoomId: State<Int> = _inviteRoomId
    fun setInviteRoomId(roomId: Int) {
        _inviteRoomId.value = roomId
    }

    private var _travelRoomInfo = mutableStateOf(TravelRoomInfoDto())
    val travelRoomInfo: State<TravelRoomInfoDto> = _travelRoomInfo

    fun putTravelRoomInfo(travelRoomInfoDto: TravelRoomInfoDto) {
        _travelRoomInfo.value = travelRoomInfoDto
        Log.d(TAG, "putTravelRoomInfo: _travelinfo : ${_travelRoomInfo.value}")
        Log.d(TAG, "putTravelRoomInfo: travelinfo : ${travelRoomInfo.value}")
    }


    private val _isShowDialog = MutableStateFlow(false)
    val isShowDialog = _isShowDialog.asStateFlow()
    fun setIsShowDialog(value: Boolean){
        viewModelScope.launch {
            _isShowDialog.emit(value)
        }
    }
    
    /**
     * 여행방 목록 조회 API
     */
    private val _networkTravelList = MutableStateFlow<NetworkResult<List<TravelRoomDto>>>(
        NetworkResult.Idle)
    val networkTravelList = _networkTravelList.asStateFlow()
    fun getTravelList() {
        viewModelScope.launch {
            _networkTravelList.emit(getTravelListUseCase.invoke())
        }
    }
    
    /**
     * 여행방 목록에서 진행 중인 여행방이 있는지 or 변경되어 있는 여행방이 있는지 확인
     */
    fun checkTravelRoom(travelRoomList: MutableList<TravelRoomDto>) {
        val travelingRoomId = getTravelingRoomId()
        
        var check = false
        travelRoomList.forEach{
            // 현재 진행 중인 방과 ID가 똑같으면서 && 진행 상태가 NOW가 아니라면 -> 진행 상태를 갱신
            if(travelingRoomId == it.roomId && it.isDone != "NOW") {
                putTravelingRoomId(0)
            }
            
            // 진행 중인 여행방 id 갱신
            if(it.isDone == "NOW") {
                putTravelingRoomId(it.roomId)
                check = true
            }
        }
        
        if(!check) putTravelingRoomId(0)
    }
}