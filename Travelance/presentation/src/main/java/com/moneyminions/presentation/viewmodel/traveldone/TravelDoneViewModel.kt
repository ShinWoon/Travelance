package com.moneyminions.presentation.viewmodel.traveldone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyminions.domain.model.NetworkResult
import com.moneyminions.domain.model.traveldone.TravelDoneInfoTotalDto
import com.moneyminions.domain.usecase.traveldone.GetTravelDoneInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TravelDoneViewModel"

@HiltViewModel
class TravelDoneViewModel @Inject constructor(
    private val travelDoneInfoUseCase: GetTravelDoneInfoUseCase,
) : ViewModel() {
    private val _travelDoneInfoGetState = MutableStateFlow<NetworkResult<TravelDoneInfoTotalDto>>(NetworkResult.Loading)
    val travelDoneInfoGetState = _travelDoneInfoGetState.asStateFlow()

    fun getTravelDoneInfo(roomId: Int) = viewModelScope.launch {
        _travelDoneInfoGetState.value = NetworkResult.Loading
        _travelDoneInfoGetState.emit(travelDoneInfoUseCase.invoke(roomId = roomId))
        Log.d(TAG, "getTravelDoneInfo: ${travelDoneInfoGetState.value}")
    }
}
