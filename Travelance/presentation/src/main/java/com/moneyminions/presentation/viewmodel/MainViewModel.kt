package com.moneyminions.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.login.JwtTokenDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.usecase.preference.GetJwtTokenUseCase
import com.moneyminions.domain.usecase.preference.GetRoleUseCase
import com.moneyminions.domain.usecase.preference.GetTravelingRoomIdUseCase
import com.moneyminions.domain.usecase.preference.PutTravelingRoomIdUseCase
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

    private var _travelRoomInfo = mutableStateOf(TravelRoomInfoDto())
    val travelRoomInfo = _travelRoomInfo.value

    fun putTravelRoomInfo(travelRoomInfoDto: TravelRoomInfoDto) {
        _travelRoomInfo.value = travelRoomInfoDto
    }


    private val _isShowDialog = MutableStateFlow(false)
    val isShowDialog = _isShowDialog.asStateFlow()
    fun setIsShowDialog(value: Boolean){
        viewModelScope.launch {
            _isShowDialog.emit(value)
        }
    }
}