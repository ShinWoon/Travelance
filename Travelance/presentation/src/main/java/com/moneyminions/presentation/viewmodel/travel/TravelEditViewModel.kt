package com.moneyminions.presentation.viewmodel.travel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.common.CommonResultDto
import com.moneyminions.domain.model.traveldetail.TravelRoomInfoDto
import com.moneyminions.domain.usecase.traveledit.EditTravelRoomInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "싸피"
@HiltViewModel
class TravelEditViewModel @Inject constructor(
    private val editTravelRoomInfoUseCase: EditTravelRoomInfoUseCase,
) : ViewModel() {
    private val _travelRoomEditState = MutableStateFlow<NetworkResult<CommonResultDto>>(NetworkResult.Idle)
    val travelRoomEditState = _travelRoomEditState.asStateFlow()

    fun editTravelRoomInfo(roomId: Int, travelRoomInfoDto: TravelRoomInfoDto) = viewModelScope.launch {
        _travelRoomEditState.value = NetworkResult.Loading
        _travelRoomEditState.emit(editTravelRoomInfoUseCase.invoke(roomId = roomId, travelRoomInfoDto = travelRoomInfoDto))
        Log.d(TAG, "editTravelRoomInfo: ${travelRoomEditState}")
    }
}